package other;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) {
//№	Имя	IP-адрес(a)	VLAN	Адрес установки	Привязанные устройства	Ном. телефона	Описание

        Pattern pattern = Pattern.compile("\\{\"status\":(\\d*),\"result\":\" <tr class='resSearch'> <td colspan='9' class='text-center'>(\\w*)");
        String s1 = " {\"status\":200,\"result\":\" <tr class='resSearch'> <td colspan='9' class='text-center'>Результат найден в разделе 'Устройства по региону'<\\/td> <\\/tr> <tr class='resSearch'  >\\n                <th scope='row'>1<\\/th>\\n                <td class='showMore'>RND-Panda<\\/td>\\n                <td>88.86.80.23<\\/td>\\n                <td>vlan321<\\/td>\\n                <td>ул. Володарского 2-я, 76<\\/td>\\n                <td>172.17.196.78 - rwr<\\/td>                                                                             \\n                <td><\\/td> \\n                <td><\\/td>                        \\n                <\\/tr>\"}";
        String s2 =  "{\"status\":200,\"map\":\"<div class='tree'> <ul> <li><div data-toggle=\\\"tooltip\\\" data-placement=\\\"auto\\\" data-html=\\\"true\\\" title=\\\" <table> <tbody>\\n                        <tr> <th scope='row'>\\u041c\\u043e\\u0434\\u0435\\u043b\\u044c:<\\/th> <td>Cisco ASR1001 <\\/td> <\\/tr> <\\/tbody> <\\/table>\\\" >\\n                         \\n                        <a  href='\\/devices\\/?show=60&reg=6' target=\\\"_blank\\\">Core Cisco<\\/a> <br>95.80.127.253\\n                        <p class=\\\"ports\\\">[0] <\\/p><\\/div> <ul> <li><div data-toggle=\\\"tooltip\\\" data-placement=\\\"auto\\\" data-html=\\\"true\\\" title=\\\" <table> <tbody>\\n                        <tr> <th scope='row'>\\u041c\\u043e\\u0434\\u0435\\u043b\\u044c:<\\/th> <td>D-Link DGS-3120-24TC<\\/td> <\\/tr> <\\/tbody> <\\/table>\\\" >\\n                        <p class=\\\"ports\\\">[1] <\\/p>\\n                        <a  href='\\/devices\\/?show=61&reg=6' target=\\\"_blank\\\">Core Switch<\\/a> <br>172.17.199.254\\n                        <p class=\\\"ports\\\">[22] <\\/p><\\/div> <ul> <li><div data-toggle=\\\"tooltip\\\" data-placement=\\\"auto\\\" data-html=\\\"true\\\" title=\\\" <table> <tbody>\\n                        <tr> <th scope='row'>\\u041c\\u043e\\u0434\\u0435\\u043b\\u044c:<\\/th> <td>D-Link DES-3200-10 Fast Ethernet Switch<\\/td> <\\/tr> <\\/tbody> <\\/table>\\\" >\\n                        <p class=\\\"ports\\\">[10] <\\/p>\\n                        <a  href='\\/devices\\/?show=62&reg=6' target=\\\"_blank\\\">Base1 ORTPC Sector switch<\\/a> <br>172.17.199.250\\n                        <p class=\\\"ports\\\">[2] <\\/p><\\/div> <ul> <li><div data-toggle=\\\"tooltip\\\" data-placement=\\\"auto\\\" title=\\\"\\u041c\\u043e\\u0434\\u0435\\u043b\\u044c:SkyMAN R5000-Omxb\\\" >\\n                <p class=\\\"ports\\\"><\\/p>\\n                <a href='\\/devices\\/?show=64&reg=6' target=\\\"_blank\\\">Base1 ORTPC RVR SectorA<\\/a> <br>172.17.196.2<p class=\\\"ports\\\">[0] <\\/p><\\/div> <ul> <li> <div data-toggle=\\\"tooltip\\\" data-placement=\\\"auto\\\" data-html=\\\"true\\\" title=\\\" <table> <tbody>\\n                        <tr> <th scope='row'>\\u041c\\u043e\\u0434\\u0435\\u043b\\u044c:<\\/th> <td>SkyMAN R5000-Sm\\/5.300.2x63.2x21<\\/td> <\\/tr>                        \\n                        <tr> <th scope='row'>VLAN:<\\/th>   <td><\\/td> <\\/tr> <\\/tbody> <\\/table>\\\" >\\n                        <p class=\\\"ports\\\">[0] <\\/p>\\n                        <a  href='\\/devices\\/?show=93&reg=6'   target=\\\"_blank\\\">RND-Panda<\\/a> <br>172.17.196.78 \\n                        <p class=\\\"ports\\\" data-toggle=\\\"tooltip\\\" data-placement=\\\"bottom\\\" data-html=\\\"true\\\" title=\\\"<span class='oi oi-person'> <\\/span>RND-Panda\\\" >[0] <\\/p> <\\/div> <\\/li>  <\\/ul> <\\/li> <\\/ul> <\\/li> <\\/ul> <\\/li> <\\/ul> <\\/li> <\\/ul> <\\/div>\"}";

        s1 = s1.replaceAll(",\"", "xXxX\"");
        s1 = s1.trim();
        s1 = s1.substring(1, s1.length()-1);

        String[] sAr = s1.split("xXxX");
        Map<String, String> result = new HashMap<>();

        for (String ss: sAr) {
            String[] temp = ss.split("\":");

            if(temp.length == 2)
                result.put(temp[0].replaceAll("\"", "").trim(), temp[1]);
            else
                result.put("Error", ss);
        }
        result.forEach((k, v) -> {
            System.out.println("Key = " + k);
            System.out.println("Value = " + v);
        });
    } // ** main()
}

// repeat text /////////////////////////////////////////////////////////////////////////////////
//            String space = " ";
//            k = String.join("", Collections.nCopies(lengthLabel - k.length(), space)) + k;
///////////////////////////////////////////////////////////////////////////////////////////////