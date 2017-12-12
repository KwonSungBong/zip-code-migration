package com.example.demo.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("local")
public class OldZipCodeSearchTest {

	@Test
	public void search() {
		RestTemplate restTemplate = new RestTemplate();

		String url = "http://chemionshop.com/exec/common/zipcoden/find";
		String keyword = "138112";
		String keywordType = "null";
		String selectPost = "6";

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("keyword", keyword);
		parameters.add("keyword_type", keywordType);
		parameters.add("selectpost", selectPost);

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);

		String response = restTemplate.postForObject(url, request, String.class);
		log.debug("result : {}", response);

		response = response.replace("\\r\\n", "");

		Map<Object,Object> map = new HashMap();
		ObjectMapper mapper = new ObjectMapper();

		try {
			//convert JSON string to Map
			map = mapper.readValue(response, new TypeReference<HashMap<Object,Object>>(){});
		} catch (Exception e) {
			log.info("Exception converting {} to map", response, e);
		}

		LinkedHashMap resultData = (LinkedHashMap)map.get("resultData");
		ArrayList listData = (ArrayList) resultData.get("listData");
		LinkedHashMap listDataGet0 = (LinkedHashMap) listData.get(0);

		listDataGet0.get("zipcode_address");
		listDataGet0.get("jibunAddress");
		listDataGet0.get("inputjibunAddress");

		log.debug("result : {}", listDataGet0);

//        http://chemionshop.com/exec/common/zipcoden/find
//        Method:POST
//        keyword:138918
//        keyword_type:null
//        selectpost:6
//        {"resultCode":"SUCC","resultMsg":"\uc131\uacf5","resultData":{"keyword":"138918","returnRequestType":"1","jibunTotalCnt":"1","streetTotalCnt":"0","sidoCntInfoData":{"1":{"sidoName":"\uc11c\uc6b8","sidoCode":1,"sidoCnt":"1"}},"sidoTotalCnt":1,"searchTotalCnt":"1","listData":[{"zipcode":"138918","zipcode_address":"\uc11c\uc6b8\ud2b9\ubcc4\uc2dc \uc1a1\ud30c\uad6c \uc7a0\uc2e46\ub3d9 \uc7a5\ubbf8\uc544\ud30c\ud2b8 (16~31\ub3d9)","jibunAddress":"\uc11c\uc6b8\ud2b9\ubcc4\uc2dc \uc1a1\ud30c\uad6c \uc7a0\uc2e46\ub3d9 \uc7a5\ubbf8\uc544\ud30c\ud2b8 (16~31\ub3d9)","inputJibunAddress":"\uc11c\uc6b8\ud2b9\ubcc4\uc2dc \uc1a1\ud30c\uad6c \uc7a0\uc2e46\ub3d9 \uc7a5\ubbf8\uc544\ud30c\ud2b8","streetBuildingName":"","jibunAddressEng":null,"inputJibunAddressEng":null,"streetAddress":"","streetAddressEng":""}]}}

	}

}
