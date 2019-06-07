package MyWork.Tools;

import com.jcraft.jsch.*;

import java.io.File;

import static MyWork.Config.SSH_PASS;
import static MyWork.Config.SSH_LOGIN;
import static MyWork.Tools.CryptDecrypt.getEncrypt;

public class SSH {
    private char[] key;
    private String host;
    private JSch jsch;
    private Session session;

    public SSH(String host, char[] pass) {
        this.host = host;
        this.key = pass;
        this.jsch = new JSch();
        this.session = null;
    }

    public boolean downloadFile(String remoteFile, String localFile) {
        boolean result = false;
        ChannelSftp sftp = null;
        try {
            File file = new File(localFile);
            long before = 0;
            if(file.exists())
                before = file.lastModified();

            session = jsch.getSession(getEncrypt(new String(key),SSH_LOGIN), host);
            UserInfo ui=new MyUserInfo(getEncrypt(new String(key),SSH_PASS));
            session.setUserInfo(ui);

            session.connect();

            sftp = (ChannelSftp) session.openChannel("sftp");
            sftp.connect();

            sftp.get(remoteFile, localFile);

            if(file.exists() && before != 0) {
                if(file.lastModified() - before > 0)
                    result = true;

            } else if (file.exists() && file.length() > 0) {
                result = true;
            }
        }
        catch (JSchException | SftpException jex) {
            jex.printStackTrace();
        } finally {
            if(sftp != null) sftp.exit();
            if(session != null) session.disconnect();
        }

        return result;
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
