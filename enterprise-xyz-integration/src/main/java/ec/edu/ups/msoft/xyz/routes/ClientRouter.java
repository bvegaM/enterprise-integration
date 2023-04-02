package ec.edu.ups.msoft.xyz.routes;

import ec.edu.ups.msoft.xyz.model.Client;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClientRouter extends RouteBuilder {

    private final JacksonDataFormat jacksonDataFormat = new JacksonDataFormat(Client.class);

    @Value("${company.digital.source.host}")
    private String systemDigitalHost;

    @Value("${company.digital.and.office.source.host}")
    private String systemDigitalAndOfficeHost;

    @Override
    public void configure() throws Exception {
        from("direct:client-transfer-route")
                .setHeader("Content-Type").constant("application/json")
                .split(body())
                .doTry()
                    .choice()
                        .when().simple("'digital' == ${body.channel}")
                            .log("Client id: ${body.id} | channel: ${body.channel}")
                            .marshal(jacksonDataFormat)
                            .to(String.format("rest:post:/clients?host=%s", systemDigitalHost))
                        .when().simple("'digitalAndOffice' == ${body.channel}")
                            .log("Client id: ${body.id} | channel: ${body.channel}")
                            .marshal(jacksonDataFormat)
                            .to(String.format("rest:post:/clients?host=%s", systemDigitalAndOfficeHost))
                        .otherwise()
                            .log("Channel not found for client: {body.id}")
                    .end()
                .endDoTry()
                .doCatch(HttpOperationFailedException.class)
                    .onWhen(exceptionMessage().contains("400"))
                    .log("Error in process client");
    }
}
