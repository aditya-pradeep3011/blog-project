package com.project.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.blog.domain.Category;
import com.project.blog.dto.CategoryRequest;
import com.project.blog.service.CategoryService;
import com.project.blog.util.TestDataUtil;

@SpringBootTest
@SpringJUnitWebConfig
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BlogUnitTests {

	private MockMvc mockMvc;
	private CategoryService categoryService;
	private ObjectMapper objectMapper;
	
	@Autowired
	public BlogUnitTests(MockMvc mockMvc, CategoryService categoryService, ObjectMapper objectMapper)
	{
		this.mockMvc = mockMvc;
		this.categoryService = categoryService;
		this.objectMapper = objectMapper;
	}

	@Test
	public void testThatGetAllCategoriesReturnsHttp200() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/category"))
			   .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testThatGetAllCategoriesReturnsAllCategories() throws Exception
	{
		Category category = TestDataUtil.createTestCategoryA();
		categoryService.addCategory(category);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/category"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty())
			   .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("TestCategoryA"));
	}
	
	@Test
	@WithMockUser(username = "adminUser", authorities = {"ROLE_USER"})
	public void testThatCreateCategoryReturnsHttp201() throws Exception
	{
		CategoryRequest category = TestDataUtil.createTestCategoryRequestA();
		String request = objectMapper.writeValueAsString(category);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/category")
											  .contentType(MediaType.APPLICATION_JSON)
											  .content(request))
			   .andExpect(MockMvcResultMatchers.status().isCreated());
		
	}
	
	@Test
	@WithMockUser(username = "adminUser", authorities = {"ROLE_USER"})
	public void testThatCreateCategoryReturnsCreatedCategory() throws Exception
	{
		CategoryRequest category = TestDataUtil.createTestCategoryRequestA();
		String request = objectMapper.writeValueAsString(category);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/category")
											  .contentType(MediaType.APPLICATION_JSON)
											  .content(request))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("TestCategoryA"));
		
	}
}
