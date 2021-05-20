package perfiltic.ecommerce.api.currency.consumer;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import perfiltic.ecommerce.api.dto.external.currency.Quotes;
import perfiltic.ecommerce.api.dto.external.currency.ResultDto;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CurrencyQuotes {

    private static final String API_KEY = "3bd83eb937473dee95c141117b0dfc46";
    private static final String baseURL =
            "http://api.currencylayer.com/live?access_key=" + API_KEY;

    final Map<String, ScheduledFuture<String>> cacheVariables =
            new HashMap<String, ScheduledFuture<String>>();
    private final String currencies = "USD,COP";
    public Quotes quotes;
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public CurrencyQuotes() {
        sendRequest();
        setCacheVariables();
    }

    /**
     * Set cache to call currency API once a day
     */
    private void setCacheVariables() {
        ScheduledFuture<String> task = executorService.schedule(() -> {
            cacheVariables.remove("yes");
            return "yes";
        }, 1, TimeUnit.DAYS);

        cacheVariables.put("yes", task);
    }

    public void convertUSDToCOP() {
        if (!cacheVariables.containsKey("yes")) {
            sendRequest();
            setCacheVariables();
        }
    }

    private void sendRequest() {
        ResultDto resultDto =
                restTemplate().getForObject(baseURL + "&currencies=" + currencies, ResultDto.class);
        if (resultDto.isSuccess())
            quotes = resultDto.getQuotes();
        else
            throw new RestClientException("Currency api call was not successful");
    }

    private RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, mappingJacksonHttpMessageConverter());
        return restTemplate;
    }

    private ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        return mapper;
    }

    private MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());
        return converter;
    }

}
