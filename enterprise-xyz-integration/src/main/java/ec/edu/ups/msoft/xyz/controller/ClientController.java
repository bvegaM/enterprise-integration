package ec.edu.ups.msoft.xyz.controller;

import ec.edu.ups.msoft.xyz.model.Client;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    private ProducerTemplate producerTemplate;

    @PostMapping("/transfer")
    public @ResponseBody ResponseEntity<Void> routeTransferClients(@RequestBody List<Client> clients){
        producerTemplate.sendBody("direct:client-transfer-route",clients);
        return ResponseEntity.ok().build();
    }
}
