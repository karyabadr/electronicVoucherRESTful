package com.buzz.restfulservice.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringWriter;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.w3c.dom.Document;

import com.buzz.electronicvoucher.ElectronicVoucherSender;
import com.buzz.electronicvoucher.schema.v1_0_0.ComprobanteRetencion;
import com.buzz.electronicvoucher.schema.v1_1_0.Factura;
import com.buzz.electronicvoucher.schema.v1_1_0.InfoTributaria;
import com.buzz.electronicvoucher.schema.v1_1_0.Factura.Retenciones.Retencion;
import com.buzz.electronicvoucher.util.Modulo11;
import com.buzz.restfulservice.model.User;
import com.buzz.restfulservice.model.Users;
import com.sun.org.apache.xerces.internal.dom.DocumentImpl;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "voucherreceptor")
@Path("/voucherreceptor")
public class VoucherReceptorService
{
	private Logger log = Logger.getLogger(VoucherReceptorService.class); 
	
	@XmlElement(name="billing")
	private String url1 = "/voucherreceptor/bill";
	
	@XmlElement(name="report")
	private String url2 = "/voucherreceptor/report";
	
	
	public String getUrl1() {
		return url1;
	}

	public void setUrl1(String url1) {
		this.url1 = url1;
	}
	
	public String getUrl2() {
		return url1;
	}

	public void setUrl2(String url1) {
		this.url1 = url1;
	}

	@GET
    @Path("/")
    @Produces("application/vnd.com.demo.user-management+xml;charset=UTF-8;version=1")
	//@Produces("application/json")
    public VoucherReceptorService getServiceInfo() {
        return new VoucherReceptorService();
    }
	
    @GET
    @Path("/users/{id}")
    @Produces("application/json")
    public Response getUserById(@PathParam("id") Integer id)
    {
    	log.info("************ENTRA AL METODO POST");
        User user = new User();
        user.setId(id);
        user.setFirstName("Lokesh");
        user.setLastName("Gupta");
        return Response.status(200).entity(user).build();
    }
    
    @POST
    @Path("/bill")
    @Consumes("application/json")
    @Produces("application/json")
    public Response processBilling(Factura pFactura)
    {
    	log.info("************ENTRA AL METODO POST BILL");
    	ElectronicVoucherSender sender = new ElectronicVoucherSender();
    	JSONObject response = sender.processVoucher(pFactura);
        return Response.status(200).entity(response.toString()).build();
        
    	/*Object obj = null;
        try{
        	obj = ((JSONObject)response.get("voucherReport")).get("generatedReport");
        }catch(Exception e){
        	
        }
        ResponseBuilder responsew = Response.ok((Object) obj);
        responsew.header("Content-Disposition", "attachment; filename=\"COMPROBANTE\"");
        return responsew.build();*/
    }
    
    @POST
    @Path("/retention")
    @Consumes("application/json")
    @Produces("application/json")
    public Response processRetention(ComprobanteRetencion pRetention)
    {
    	log.info("************ENTRA AL METODO POST RETENTION");
    	ElectronicVoucherSender sender = new ElectronicVoucherSender();
    	JSONObject response = sender.processVoucher(pRetention);
        return Response.status(200).entity(response.toString()).build();
    }
    
    @POST
    @Path("/billtest")
    @Consumes("application/json")
    @Produces("application/json")
    public Response processBillingtest(Factura pFactura)
    {
    	
        return Response.status(200).entity(pFactura).build();
    }
    
    @POST
    @Path("/retentiontest")
    @Consumes("application/json")
    @Produces("application/xml")
    public Response processRetentiontest(ComprobanteRetencion pRetention)
    {
    	
        return Response.status(200).entity(pRetention).build();
    }
    
    @GET
    @Path("/report/{accessKey}")
    @Produces("application/pdf")
    public Response getFileInPDFFormat(@PathParam("accessKey") String accessKey) 
    {
        //System.out.println("File requested is : " + fileName);
    	log.info("************OBTIENE REPORTE PARA KEY: "+accessKey);
         
        //Put some validations here such as invalid file name or missing file name
        if(accessKey == null || accessKey.isEmpty())
        {
            ResponseBuilder response = Response.status(Status.BAD_REQUEST);
            return response.build();
        }
         
        //Prepare a file object with file to return
        File file = new File("/Users/santteegt/Desktop/FACTURAELECTRONICA.pdf");
         
        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename=\"COMPROBANTE-"+accessKey +"\"");
        return response.build();
    }
    
    @POST
    @Path("/users")
    @Consumes("application/json")
    @Produces("application/json")
    public Response processUsers(Users users)
    {
    	log.info("************ENTRA AL METODO POST");
        return Response.status(200).entity(users).build();
    }
    
    
}