package MyWork.NodesClass;

import MyWork.Tools.PassGenerate;
import MyWork.Tools.SSH;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;

import java.io.IOException;
import java.io.InputStream;

import static MyWork.Config.MB_PASS;

public class Mobibox {
    private String ipMb;
    private char[] key;
    private SSH ssh;

    public Mobibox(String ip, char[] key) {
        this.ipMb = ip;
        this.key = key;
        this.ssh = new SSH(this.ipMb, this.key);
    } // ** constructor

    public String send(String command) {
        String result = "";
        StringBuilder out = new StringBuilder();

        ChannelExec exec = ssh.getExec("admin", MB_PASS);
        exec.setCommand(command);

        try (InputStream in = exec.getInputStream()) {
            exec.connect();

            byte[] tmp = new byte[4096];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 4096);

                    if(i < 0)
                        break;
                    out.append(new String(tmp, 0, i));
                } // ** while read
                if(exec.isClosed()) {
                    System.out.println("exit status: " + exec.getExitStatus());
                    break;
                }
            } // while connected
            result = out.toString();

        } catch (IOException | JSchException ex) {
            ex.printStackTrace();
        } finally {
            ssh.closeSession();
        }
        return result;
    } // ** send()

    public String createCustomer(Customer customer, String ipNewMb) {
        String result = "[Error] not found MB line in city.";
        StringBuilder commands = new StringBuilder();
        // "95.80.120.44X172.17.239.129X172.16.44.235X28"
        String[] mbData = customer.getCity().getLanMB().split("X");
        if(mbData.length == 4) {
            String newPass = PassGenerate.getPass();
            String gwMb = mbData[1];

            //ppp sec add name="Kr-IPDyachkoMB" service=l2tp password="k4hfJ94TS4bfH7rHnfL9fg3J9frtFMujl8c49" remote-address=172.17.151.21 local-address=172.17.151.1
            ///interface eoip add remote-address=172.17.151.21 tunnel-id=326  name=Kr-IPDyachkoMB disabled=no !keepalive
            ///interface bridge port add bridge=bridgeUnnumbered horizon=3 interface=Kr-IPDyachkoMB

            commands.append("ppp sec add name=\"")
                    .append(customer.getMnemokod())
                    .append("\" service=l2tp password=\"")
                    .append(newPass)
                    .append("\" remote-address=")
                    .append(ipNewMb)
                    .append(" local-address=")
                    .append(gwMb)
                    .append("\n");

            commands.append("interface eoip add remote-address=")
                    .append(ipNewMb)
                    .append(" tunnel-id=")
                    .append(customer.getVlan())
                    .append(" name=")
                    .append(customer.getMnemokod())
                    .append(" disabled=no !keepalive")
                    .append("\n");

            commands.append("interface bridge port add bridge=bridgeUnnumbered horizon=3 interface=")
                    .append(customer.getMnemokod());

            ///interface l2tp-client add add-default-route=yes connect-to=95.80.98.181 disabled=no keepalive-timeout=disabled name=l2tp-out2 password=Nh78dkju129jfguhndfSD user=Kr-IPDyachkoMB
            ///ip dhcp-client set default-route-distance=10 numbers=0
            result = "interface l2tp-client add add-default-route=yes connect-to="
                    + ipMb
                    + " disabled=no keepalive-timeout=disabled name=l2tp-out2 password="
                    + newPass
                    + " user="
                    + customer.getMnemokod()
                    + "\n"
                    + "ip dhcp-client set default-route-distance=10 numbers=0\n\n";

            result += send(commands.toString());
        }
//        System.out.println(commands.toString());

        return result;
    }

    public String removeCustomer(Customer customer) {
        String result;
        String commands = "ppp secret remove " + customer.getMnemokod() + "\n";
        commands += "int eoip remove " + customer.getMnemokod();

        result = send(commands);

        return result;
    } // ** removeCustomer()

    public static void main(String[] args) {
        String ip = "ip";
        char[] key = "pass".toCharArray();
        Customer customer = new Customer("Orel", "Orel-test", "4000", "null", "null", "null");

        Mobibox mobibox = new Mobibox(ip, key);
//        String mbS = mobibox.createCustomer(customer, "172.17.239.187");
//        System.out.println(mbS);

//        String rem = mobibox.removeCustomer(customer);
//        System.out.println(rem);
//        mobibox.send("log pr\n int pr");

    }
} // ** class Mobibox
