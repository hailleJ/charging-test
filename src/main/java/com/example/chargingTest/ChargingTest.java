package com.example.chargingTest;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class ChargingTest {


private RestTemplate restTemplate = new RestTemplate();
private  String maUrl = "http://10.175.206.42/soap-payment-api/ws/AmountChargingService/services/chargeAmount";






    @PostConstruct
    private  void chargingTest() {

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(maUrl, new HttpEntity<>(getPaymentBody(), headers()), String.class);
            log.info("successful charging response:: {}", response.getBody());
        } catch (Exception e) {
            log.error("response body:: {}", e.getMessage());
        }

    }


    public  String getPaymentBody() {
        String spId = "015553";
        // String password = "";
        String serviceId = "0155532000014451";
        String timeStamp = "20230523141200";
        String number = "251913769328";
        String price = "2";
        String spPassword = "a2a9d1aa14fbae0af482df54f64acbd0"; //getMd5(spId+password+timeStamp);

        return "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/'" +
                " xmlns:loc='http://www.csapi.org/schema/parlayx/payment/amount_charging/v3_1/local' ><soapenv:Header>" +
                "<tns:RequestSOAPHeader xmlns:tns='http://www.huawei.com.cn/schema/common/v2_1'>" +
                "<tns:spId>" + spId + "</tns:spId>" +
                "<tns:spPassword>" + spPassword + "</tns:spPassword>" +
                "<tns:timeStamp>" + timeStamp + "</tns:timeStamp>" +
                "<tns:serviceId>" + serviceId + "</tns:serviceId>" +
                "<tns:OA>" + number + "</tns:OA>" +
                "<tns:FA>" + number + "</tns:FA>" +
                "</tns:RequestSOAPHeader> </soapenv:Header>" +
                "<soapenv:Body><loc:chargeAmount>" +
                "<loc:endUserIdentifier>" + number + "</loc:endUserIdentifier>" +
                "<loc:charge>" +
                "<description>charged</description>" +
                "<currency>Birr</currency>" +
                "<amount>" + price + "00</amount>" +
                "<code>255</code>" +
                "</loc:charge>" +
                "<loc:referenceCode>255</loc:referenceCode>" +
                "</loc:chargeAmount></soapenv:Body></soapenv:Envelope>";
    }

    private static HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        return headers;
    }
}
