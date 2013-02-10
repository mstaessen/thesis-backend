package com.capgemini.expense;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.xml.XmlNamespaceDictionary;
import com.google.api.client.xml.XmlObjectParser;

@Path("/currencyService")
public class CurrencyService {

	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final String ACCESS_CONTROL_ALLOW_ORIGIN_HEADER = "Access-Control-Allow-Origin";
	private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "*";
	private static final String ACCESS_CONTROL_ALLOW_HEADER = "Access-Control-Allow-Headers";
	private static final String ACCESS_CONTROL_ALLOW = "X-Requested-With";
	
	@OPTIONS
    @Path("/getCurrencies")
    public void getCurrencies(@Context HttpServletResponse response, @Context HttpServletRequest req) {
        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, ACCESS_CONTROL_ALLOW_ORIGIN);
        response.setHeader(ACCESS_CONTROL_ALLOW_HEADER, ACCESS_CONTROL_ALLOW);
    }


	@POST
	@Path("/getCurrencies")
	@Produces("application/xml")
	public Response getCurrencies() {

		HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
			@Override
			public void initialize(HttpRequest request) {
				XmlNamespaceDictionary xmlNamespaceDictionary = new XmlNamespaceDictionary();
				request.setParser(new XmlObjectParser(xmlNamespaceDictionary));
			}
		});
		
		try {
			HttpRequest request = requestFactory.buildGetRequest(new GenericUrl("http://www.ecb.int/stats/eurofxref/eurofxref-daily.xml"));
			String xml = request.execute().parseAsString();
			ResponseBuilder builder = Response.ok(xml);
			builder.header(ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, ACCESS_CONTROL_ALLOW_ORIGIN);
			builder.header(ACCESS_CONTROL_ALLOW_HEADER, ACCESS_CONTROL_ALLOW);
			return builder.build();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;

	}

}
