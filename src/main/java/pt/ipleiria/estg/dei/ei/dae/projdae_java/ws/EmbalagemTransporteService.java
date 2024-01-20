package pt.ipleiria.estg.dei.ei.dae.projdae_java.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.EmbalagemTransporteDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.EncomendaDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs.EmbalagemTransporteBean;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EmbalagemTransporte;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.security.Authenticated;

import java.util.List;

@Path("embalagemTransporte") // relative url web path for this service
@Produces({MediaType.APPLICATION_JSON}) // injects header “Content-Type: application/json”
@Consumes({MediaType.APPLICATION_JSON}) // injects header “Accept: application/json”
@Authenticated
@RolesAllowed("Administrador")
public class EmbalagemTransporteService {
    @EJB
    private EmbalagemTransporteBean embalagemTransporteBean;

    @Inject
    private SensorService sensorService;


    public EmbalagemTransporteDTO toDTO(EmbalagemTransporte embalagemTransporte){
        List<SensorDTO> sensores = sensorService.sensorToDTOs(embalagemTransporte.getSensores());

        return new EmbalagemTransporteDTO(
                embalagemTransporte.getId(),
                embalagemTransporte.getTipo(),
                embalagemTransporte.getFuncao(),
                embalagemTransporte.getDataFabrico(),
                embalagemTransporte.getMaterial(),
                embalagemTransporte.getPeso(),
                embalagemTransporte.getVolume(),
                sensores);
    }


    @GET
    @Path("/")
    public EmbalagemTransporteDTO getEmbalagemTransporte() {
        return toDTO(embalagemTransporteBean.getEmbalagemTransporte());
    }


    @PUT
    @Path("/")
    public Response updateEmbalagemTransporte(EmbalagemTransporteDTO embalagemTransporteDTO) {
        try {
            embalagemTransporteBean.update(embalagemTransporteDTO.getId(), embalagemTransporteDTO.getTipo(), embalagemTransporteDTO.getFuncao(), embalagemTransporteDTO.getMaterial(), embalagemTransporteDTO.getPeso(), embalagemTransporteDTO.getVolume(), embalagemTransporteDTO.getSensores());
            return Response.ok(toDTO(embalagemTransporteBean.getEmbalagemTransporte())).build();
        } catch (MyEntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

}
