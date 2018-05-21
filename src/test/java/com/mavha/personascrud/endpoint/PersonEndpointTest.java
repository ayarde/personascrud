package com.mavha.personascrud.endpoint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mavha.personascrud.domain.Person;
import com.mavha.personascrud.service.PersonService;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonEndpointTest {

    private static Dispatcher dispatcher;
    private static PersonEndpoint personEndpoint;
    @Mock
    private PersonService personService;
    private List<Person> personList;

    @Before
    public void setup () {
        MockitoAnnotations.initMocks(getClass());
        dispatcher = MockDispatcherFactory.createDispatcher();
        personEndpoint = new PersonEndpoint(personService);
        dispatcher.getRegistry().addSingletonResource(personEndpoint);

        personList = new ArrayList<>();

        Person person = new Person();
        person.setDni(1234);
        person.setName("Pepe");
        person.setLastName("Argento");
        person.setAge(55);

        personList.add(person);
    }

    @After
    public void removeResource() {
        dispatcher.getRegistry().removeRegistrations(PersonEndpoint.class);
    }

    @Test
    public void shouldSavePerson() throws Exception {

        MockHttpRequest request = createRequest("/",createRequestJSON(1234));

        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
        when(personService.createPerson(personCaptor.capture())).thenAnswer(new ArgumentAnswer<Person>(0));

        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        verify(personService).createPerson(any(Person.class));
        Person person = personCaptor.getValue();

        assertEquals(1234 ,person.getDni());
        assertEquals(201, response.getStatus());
    }

    @Test
    public void shouldSavePersonWithParameter() throws Exception {

        MockHttpRequest request = createRequestParameter("/",1234);

        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
        when(personService.createPerson(personCaptor.capture())).thenAnswer(new ArgumentAnswer<Person>(0));

        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        verify(personService).createPerson(any(Person.class));
        Person person = personCaptor.getValue();

        assertEquals(1234 ,person.getDni());
        assertEquals(201, response.getStatus());
    }

    @Test
    public void shouldGetPersons() throws Exception {

        MockHttpRequest request = createRequestToGet("/");

        when(personService.findPersons()).thenReturn(personList);

        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals(false, response.getContentAsString().isEmpty());
        assertEquals(200, response.getStatus());
    }

    public String getPrettyPrintJSON(String input) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(input).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }

    public String createRequestJSON(int dni) {
        Person person = new Person();
        person.setDni(dni);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(person);
    }

    public String createResponseJSON(int dni ) {
        Person person = new Person();
        person.setDni(dni);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(person);
    }

    public MockHttpRequest createRequestParameter(
            String path,
            int requestBody) throws URISyntaxException, JAXBException {

        MockHttpRequest request = MockHttpRequest.post(path + requestBody);
        return request;
    }

    public MockHttpRequest createRequest(
            String path,
            String requestBody) throws URISyntaxException, JAXBException {

        MockHttpRequest request = MockHttpRequest.post(path);
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON_TYPE);
        request.content(requestBody.getBytes());

        return request;
    }

    public MockHttpRequest createRequestToGet(String path) throws URISyntaxException, JAXBException {

        MockHttpRequest request = MockHttpRequest.get(path);
        return request;
    }
}
