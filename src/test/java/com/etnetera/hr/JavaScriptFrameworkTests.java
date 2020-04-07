package com.etnetera.hr;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Class used for Spring Boot/MVC based tests.
 *
 * @author Etnetera
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class JavaScriptFrameworkTests {

	private String nameOne = "ReactJS";
	private String versionOne = "3.5";
	private Long deprecationDateOne =  new SimpleDateFormat("dd.MM.yyyy").parse("1.1.2030").getTime();
	private Double hypeOne = 5d;
	private String nameTwo = "Vue.js";

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private JavaScriptFrameworkRepository repository;

	public JavaScriptFrameworkTests() throws ParseException {

	}

	private void prepareData() throws Exception {
		JavaScriptFramework react = new JavaScriptFramework(nameOne , versionOne, deprecationDateOne, hypeOne);
		JavaScriptFramework vue = new JavaScriptFramework(nameTwo, null, null, null);

		repository.save(react);
		repository.save(vue);
	}

	@Test
	public void frameworksTest() throws Exception {
		prepareData();

		mockMvc.perform(get("/frameworks/all")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is(nameOne)))
				.andExpect(jsonPath("$[0].version", is(versionOne)))
				.andExpect(jsonPath("$[0].deprecationDate", is(deprecationDateOne)))
				.andExpect(jsonPath("$[0].hype", is(hypeOne)))
				.andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].name", is(nameTwo)))
				.andExpect(jsonPath("$[1].version", is(nullValue())))
				.andExpect(jsonPath("$[1].deprecationDate", is(nullValue())))
				.andExpect(jsonPath("$[1].hype", is(nullValue())));
		;
	}

	@Test
	public void deleteFrameworksTest() throws Exception {
		prepareData();

		mockMvc.perform(delete("/frameworks/delete/1"));
		mockMvc.perform(get("/frameworks/all")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(2)))
				.andExpect(jsonPath("$[0].name", is(nameTwo)))
				.andExpect(jsonPath("$[0].version", is(nullValue())))
				.andExpect(jsonPath("$[0].deprecationDate", is(nullValue())))
				.andExpect(jsonPath("$[0].hype", is(nullValue())));
	}

	@Test
	public void findByIdTest() throws Exception {
		prepareData();
		mockMvc.perform(get("/frameworks/find/1")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is(nameOne)))
				.andExpect(jsonPath("$[0].version", is(versionOne)))
				.andExpect(jsonPath("$[0].deprecationDate", is(deprecationDateOne)))
				.andExpect(jsonPath("$[0].hype", is(hypeOne)));
		mockMvc.perform(get("/frameworks/find/2")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(2)))
				.andExpect(jsonPath("$[0].name", is(nameTwo)))
				.andExpect(jsonPath("$[0].version", is(nullValue())))
				.andExpect(jsonPath("$[0].deprecationDate", is(nullValue())))
				.andExpect(jsonPath("$[0].hype", is(nullValue())));
	}

	@Test
	public void addFrameworkInvalid() throws JsonProcessingException, Exception {
		JavaScriptFramework framework = new JavaScriptFramework();
		mockMvc.perform(post("/frameworks/add").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors", hasSize(1)))
				.andExpect(jsonPath("$.errors[0].field", is("name")))
				.andExpect(jsonPath("$.errors[0].message", is("NotEmpty")));

		framework.setName("verylongnameofthejavascriptframeworkjavaisthebest");
		mockMvc.perform(post("/frameworks/add").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(framework)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.errors", hasSize(1)))
				.andExpect(jsonPath("$.errors[0].field", is("name")))
				.andExpect(jsonPath("$.errors[0].message", is("Size")));

	}

	@Test
	public void changeFrameworksTest() throws Exception {
		prepareData();
		Integer id = 1;
		String name = "Vue.JS2";
		String version = "5";
		Long deprecationDate = 1899952400000L;
		Double hype = 10d;

		// commented variables prepared for potential future use
		mockMvc.perform(put("/frameworks/change?id="+id.toString()+/*"&name="+name+*/"&version="+version+"&hype="+hype.toString()/*+"&deprecationDate="+deprecationDate.toString()*/));
		mockMvc.perform(get("/frameworks/all")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(id)))
				.andExpect(jsonPath("$[0].name", is(nameOne)))
				.andExpect(jsonPath("$[0].version", is(version)))
				.andExpect(jsonPath("$[0].deprecationDate", is(deprecationDateOne)))
				.andExpect(jsonPath("$[0].hype", is(hype)));
	}

	@Test
	public void findByNameTest() throws Exception {
		prepareData();
		mockMvc.perform(get("/frameworks/findByName?name="+nameOne)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is(nameOne)))
				.andExpect(jsonPath("$[0].version", is(versionOne)))
				.andExpect(jsonPath("$[0].deprecationDate", is(deprecationDateOne)))
				.andExpect(jsonPath("$[0].hype", is(hypeOne)));



	}
	@Test
	public void findByHypeTest() throws Exception {
		prepareData();
		mockMvc.perform(get("/frameworks/findByHype?hype="+3)).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is(nameOne)))
				.andExpect(jsonPath("$[0].version", is(versionOne)))
				.andExpect(jsonPath("$[0].deprecationDate", is(deprecationDateOne)))
				.andExpect(jsonPath("$[0].hype", is(hypeOne)));
	}
}
