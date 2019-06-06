package other;


import com.jcraft.jsch.*;

public class test {
    public static void main(String[] args) {
        JSch jsch = new JSch();

        String remoteClientsConf = "/etc/Clients.conf";
        String localClientConf = "Clients.conf";
//        String knownHosts = "C:\\known_hosts.txt";
        ChannelSftp sftp = null;
        Session session = null;

        try {
//            jsch.setKnownHosts(knownHosts);

            session = jsch.getSession("user", "213.170.117.254");
//            session.setPassword("pass");

            UserInfo ui=new MyUserInfo("pass");
            session.setUserInfo(ui);

            session.connect();

            sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();

            sftp.get(remoteClientsConf, localClientConf );
// OR
//            InputStream in = sftp.get( "remote-file" );
            // process inputstream as needed



        } catch (JSchException | SftpException jex) {
            jex.printStackTrace();
        } finally {
            if(sftp != null) sftp.exit();
            if(session != null) session.disconnect();
        }


    } // ** main()

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

} // ** class test


// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////