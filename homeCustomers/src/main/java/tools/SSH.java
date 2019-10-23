package tools;

import com.jcraft.jsch.*;
import java.io.*;

import static java.lang.Thread.sleep;
import static tools.CryptDecrypt.getEncrypt;
import static tools.Config.*;

public class SSH {
    private char[] key;
    private String host;
    private JSch jsch;
    private Session session;

    public SSH(String host, char[] pass) {
//        System.out.println("SSH create host: " + host);
        this.host = host;
        this.key = pass;
        this.jsch = new JSch();
        this.session = null;
    }

    private void sessionConnect() throws JSchException {
        session = jsch.getSession(getEncrypt(new String(key), SSH_LOGIN), host);
        UserInfo ui = new MyUserInfo(getEncrypt(new String(key), SSH_PASS_D));
        session.setUserInfo(ui);

        session.connect();
    } // ** sessionConnect()

    private void sessionConnect(String login, String pass) throws JSchException {
        session = jsch.getSession(login, host);
        UserInfo ui = new MyUserInfo(getEncrypt(new String(key), pass));
        session.setUserInfo(ui);

        session.connect();
    } // ** sessionConnect()

    public boolean downloadFile(String remoteFile, String localFile) {
        // or downloadFileSCP(remoteFile, localFile);
        return downloadFileSFTP(remoteFile, localFile);
    }

    private boolean downloadFileSFTP(String remoteFile, String localFile) {
        boolean result = false;
        ChannelSftp sftp = null;
        try {
            File file = new File(localFile);
            long before = 0;
            if (file.exists())
                before = file.lastModified();

            sessionConnect();

            sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();

            sftp.get(remoteFile, localFile);

            if (file.exists() && before != 0) {
                if (file.lastModified() - before > 0)
                    result = true;

            } else if (file.exists() && file.length() > 0) {
                result = true;
            }
        } catch (JSchException | SftpException jex) {
            jex.printStackTrace();
        } finally {
            if (sftp != null) sftp.exit();
            closeSession();
        }

        return result;
    } // ** downloadFileSFTP

    private boolean downloadFileSCP(String remoteFile, String localFile) {
        FileOutputStream fos = null;
        try {
            String rfile = remoteFile;
            String lfile = localFile;

            String prefix = null;
            if (new File(lfile).isDirectory()) {
                prefix = lfile + File.separator;
            }

            sessionConnect();

            // exec 'scp -f rfile' remotely
            rfile = rfile.replace("'", "'\"'\"'");
            rfile = "'" + rfile + "'";
            String command = "scp -f " + rfile;
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            // get I/O streams for remote scp
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();

            channel.connect();

            byte[] buf = new byte[1024];

            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();

            while (true) {
                int c = checkAck(in);
                if (c != 'C') {
                    break;
                }

                // read '0644 '
                in.read(buf, 0, 5);

                long filesize = 0L;
                while (true) {
                    if (in.read(buf, 0, 1) < 0) {
                        // error
                        break;
                    }
                    if (buf[0] == ' ') break;
                    filesize = filesize * 10L + (long) (buf[0] - '0');
                }

                String file;
                for (int i = 0; ; i++) {
                    in.read(buf, i, 1);
                    if (buf[i] == (byte) 0x0a) {
                        file = new String(buf, 0, i);
                        break;
                    }
                }

                //System.out.println("filesize="+filesize+", file="+file);

                // send '\0'
                buf[0] = 0;
                out.write(buf, 0, 1);
                out.flush();

                // read a content of lfile
                fos = new FileOutputStream(prefix == null ? lfile : prefix + file);
                int foo;
                while (true) {
                    if (buf.length < filesize) foo = buf.length;
                    else foo = (int) filesize;
                    foo = in.read(buf, 0, foo);
                    if (foo < 0) {
                        // error
                        break;
                    }
                    fos.write(buf, 0, foo);
                    filesize -= foo;
                    if (filesize == 0L) break;
                }
                fos.close();
                fos = null;

                if (checkAck(in) != 0) {
                    System.exit(0);
                }

                // send '\0'
                buf[0] = 0;
                out.write(buf, 0, 1);
                out.flush();
            }

            closeSession();

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (fos != null) fos.close();
            } catch (Exception ee) {
            }
        }

        return true;
    }

    public ChannelExec getExec(String login, String pass) {
        ChannelExec channelExec = null;
        try {
            sessionConnect(login, pass);
            channelExec = (ChannelExec) session.openChannel("exec");
        } catch (JSchException ex) {
            ex.printStackTrace();
        }

        return channelExec;
    } // ** getExec()

    public ChannelExec getExec() {
        ChannelExec channelExec = null;
        try {
            sessionConnect();
            channelExec = (ChannelExec) session.openChannel("exec");
        } catch (JSchException ex) {
            ex.printStackTrace();
        }

        return channelExec;
    } // ** getExec()

    public String telnetConnection(String[] commands, String user, String password, String host) {
        String result = "[Error telnet ...]" + host;
        DataInputStream dataIn = null;
        DataOutputStream dataOut = null;
        Channel channel = null;
        try {
            if (session == null) {
                this.sessionConnect();
            }

            channel = session.openChannel("shell");
            channel.connect(3000);

            dataIn = new DataInputStream(channel.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(dataIn));
            dataOut = new DataOutputStream(channel.getOutputStream());

//            System.out.println("Starting telnet connection...");
            dataOut.writeBytes("telnet " + host + "\n");
            dataOut.writeBytes(user + "\n");
            dataOut.writeBytes(password + "\n");

            for (String command : commands) {
//                System.out.println(command);
                dataOut.writeBytes(command + "\n");
            }
//        dataOut.writeBytes("telnet localhost 4242\r\n");
////      dataOut.writeBytes("enable\r\n");
//        dataOut.writeBytes(command+"\r\n");
            dataOut.writeBytes("exit\r\n"); //exit from telnet
            dataOut.flush();
            sleep(10);
            dataOut.writeBytes("exit\r\n"); //exit from telnet
            dataOut.flush();
            sleep(10);
            dataOut.writeBytes("exit\r\n"); //exit from shell
            dataOut.flush();


//            System.out.println("Data has transmitted.");

            String line = reader.readLine();
            result = line + "\n";

//            while (!(line = reader.readLine()).equals("Connection closed by foreign host")) {
            while ((line = reader.readLine()) != null && !line.contains("Connection closed by foreign host.")) {
//                System.out.println(line);
                result += line + "\n";
            }


        } catch (IOException | JSchException | InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            try {
                dataIn.close();
                dataOut.close();
                channel.disconnect();
            } catch (Exception ex) {}
        }

        return result;

    }

    public String shellConnection(String[] commands) {
        String result = "[Error] connect to ... " + host;
        DataInputStream dataIn = null;
        DataOutputStream dataOut = null;
        Channel channel = null;
        try {
            if (session == null) {
                this.sessionConnect();
            }

            channel = session.openChannel("shell");
            channel.connect(3000);

            dataIn = new DataInputStream(channel.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(dataIn));
            dataOut = new DataOutputStream(channel.getOutputStream());

//            System.out.println("Starting remote connection...");
            dataOut.writeBytes("ssh " + getEncrypt(new String(key), SSH_LOGIN) + "@"+ getEncrypt(new String(key), QESM) +"\r\n");
//            System.out.println("Has connected to " + getEncrypt(new String(key), QESM));

            for (String command : commands) {
//                System.out.println(command);
                dataOut.writeBytes(command + "\n");
            }

            dataOut.writeBytes("exit\r\n"); //exit from remote
            dataOut.flush();
            sleep(10);
            dataOut.writeBytes("exit\r\n"); //exit from shell
            dataOut.flush();

//            System.out.println("Data has transmitted.");

            String line = reader.readLine();
            result = line + "\n";

//            while (!(line = reader.readLine()).equals("Connection closed by foreign host")) {
            while ((line = reader.readLine()) != null && !line.contains("closed.")) {
//                System.out.println(line);
                result += line + "\n";
            }

//            System.out.println("[End reading]");

        } catch (IOException | JSchException | InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            try {
                dataIn.close();
                dataOut.close();
                channel.disconnect();
                session.disconnect();
            } catch (Exception ex) {}
        }

        return result;

    }

    public void closeSession() {
        if (session != null) {
            session.disconnect();
            session = null;
        }
    }

    private static int checkAck(InputStream in) throws IOException {
        int b = in.read();
        // b may be 0 for success,
        //          1 for error,
        //          2 for fatal error,
        //          -1
        if (b == 0) return b;
        if (b == -1) return b;

        if (b == 1 || b == 2) {
            StringBuilder sb = new StringBuilder();
            int c;
            do {
                c = in.read();
                sb.append((char) c);
            }
            while (c != '\n');
            if (b == 1) { // error
                System.out.print(sb.toString());
            }
            if (b == 2) { // fatal error
                System.out.print(sb.toString());
            }
        }
        return b;
    }

    public static class MyUserInfo implements UserInfo, UIKeyboardInteractive {
        String passwd;

        MyUserInfo(String pass) {
            passwd = pass;
        }

        @Override
        public String getPassphrase() {
            return null;
        }

        @Override
        public String getPassword() {
            return passwd;
        }

        @Override
        public boolean promptPassphrase(String arg0) {
            return false;
        }

        @Override
        public boolean promptPassword(String arg0) {
            return true;
        }

        @Override
        public boolean promptYesNo(String arg0) {
            return true;
        }

        @Override
        public void showMessage(String arg0) {
        }

        @Override
        public String[] promptKeyboardInteractive(String arg0, String arg1,
                                                  String arg2, String[] arg3, boolean[] arg4) {
            return null;
        }
    } // ** class MyUserInfo
}
