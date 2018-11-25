package don.petruchio.app9696;


import jdk.nashorn.internal.ir.RuntimeNode;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class NumberController {

    private String resTemplate = "name : %s; number : %s; date : %s;\n";

    @RequestMapping("/")
    public String readField(@RequestParam Map<String,String> params)
    {
        PageData pageData=null;
        String app9797 = "http://localhost:9797/";
        RestTemplate restTemplate = new RestTemplate();
        if (params.get("name")==null && params.get("number")==null ||
                params.get("name").isEmpty() && params.get("number").isEmpty())
        {
            pageData=new PageData("","");
            ParameterizedTypeReference<ArrayList<Record>> typeRef = new ParameterizedTypeReference<ArrayList<Record>>() {};
            ResponseEntity<ArrayList<Record>> response;
            try {
                response = restTemplate.exchange(app9797,
                        HttpMethod.POST,
                        new HttpEntity<PageData>(pageData),
                        typeRef);
            }catch (HttpStatusCodeException e)
            {
                return "ERROR "+e.getStatusCode().toString()+"\n";
            }
            if (response.getStatusCode()==HttpStatus.OK) {
                ArrayList<Record> result = response.getBody();
                String allLines = "";
                if (result != null)
                    for(Record record : result)
                    {
                        allLines += String.format(resTemplate, record.getName(),
                                record.getNumber(), record.getDate());
                    }
                return allLines;
            }
            else
            {
                return "Select error "+response.getStatusCode().toString()+"\n";
            }
        }
        else
        {
            pageData=new PageData(params.get("name"), params.get("number"));
            ResponseEntity<ArrayList> response;
            try {
                response = restTemplate.exchange(app9797,
                        HttpMethod.POST,
                        new HttpEntity<PageData>(pageData),
                        ArrayList.class);
                if (response.getStatusCode()==HttpStatus.OK)
                {
                    return "Record Added "+response.getStatusCode().toString()+"\n";
                }
                else
                {
                    return "Writing error"+response.getStatusCode().toString()+"\n";
                }
                }catch (HttpStatusCodeException e) {
                return "ERROR "+e.getStatusCode().toString()+"\n";
            }
        }
        //return "OK\n";

    }

}
