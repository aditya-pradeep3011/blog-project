package com.project.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.project.blog.domain.Category;
import com.project.blog.service.CategoryService;
import com.project.blog.util.TestDataUtil;

@SpringBootTest
@SpringJUnitWebConfig
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BlogUnitTests {

	private MockMvc mockMvc;
	private CategoryService categoryService;
	
	@Autowired
	public BlogUnitTests(MockMvc mockMvc, CategoryService categoryService)
	{
		this.mockMvc = mockMvc;
		this.categoryService = categoryService;
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
}
