package pt.ipleiria.estg.dei.ei.dae.projdae_java.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Sensor;

import java.util.List;
import java.util.stream.Collectors;

@Path("sensores") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
public class SensorService {
    @EJB
    SensorBean sensorBean;

    @GET
    @Path("/ativos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SensorDTO> getActiveSensors() {
        return sensorToDTOs(sensorBean.getSensoresAtivos());
    }

    public List<SensorDTO> sensorToDTOs(List<Sensor> sensores) {
        return sensores.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public SensorDTO toDTO(Sensor sensor){
        Long encomendaId = sensor.getEncomenda() != null ? sensor.getEncomenda().getId() >= 0 ? sensor.getEncomenda().getId() :null : null;
        return new SensorDTO(
                sensor.getId(),
                sensor.getNome(),
                encomendaId,
                sensorBean.getUltimaObservacao(sensor));
    }
}
