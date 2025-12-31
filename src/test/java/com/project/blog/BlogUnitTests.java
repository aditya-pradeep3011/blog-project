package com.project.blog;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.project.blog.domain.Post;
import com.project.blog.domain.Tag;
import com.project.blog.domain.User;
import com.project.blog.dto.CategoryRequest;
import com.project.blog.dto.CreatePostRequest;
import com.project.blog.dto.TagRequest;
import com.project.blog.service.CategoryService;
import com.project.blog.service.PostService;
import com.project.blog.service.TagService;
import com.project.blog.service.UserService;
import com.project.blog.util.TestDataUtil;

@SpringBootTest
@SpringJUnitWebConfig
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class BlogUnitTests {

	private MockMvc mockMvc;
	private CategoryService categoryService;
	private TagService tagService;
	private ObjectMapper objectMapper;
	private PostService postService;
	private UserService userService;
	
	@Autowired
	public BlogUnitTests(MockMvc mockMvc, CategoryService categoryService, ObjectMapper objectMapper, TagService tagService, PostService postService, UserService userService)
	{
		this.mockMvc = mockMvc;
		this.categoryService = categoryService;
		this.objectMapper = objectMapper;
		this.tagService = tagService;
		this.postService = postService;
		this.userService = userService;
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
	
	@Test
	@WithMockUser(username = "adminUser", authorities = {"ROLE_USER"})
	public void testThatDeleteCategoryReturnsHttp204() throws Exception
	{
		Category category = TestDataUtil.createTestCategoryA();
		categoryService.addCategory(category);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/category/"+category.getId()))
			   .andExpect(MockMvcResultMatchers.status().isNoContent());
		
	}
	
	@Test
	public void testThatGetAllTagsReturnsHttp200() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tag"))
			   .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testThatGetAllTagsReturnsAllTags() throws Exception
	{
		Tag tag = TestDataUtil.createTestTagA();
		Set<String> tagNames = new HashSet<>();
		tagNames.add(tag.getName());
		tagService.createTags(tagNames);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/tag"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty())
			   .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("TestTagA"));
	}
	
	@Test
	@WithMockUser(username = "adminUser", authorities = {"ROLE_USER"})
	public void testThatCreateTagReturnsHttp201() throws Exception
	{
		TagRequest tag = TestDataUtil.createTestTagRequestA();
		String request = objectMapper.writeValueAsString(tag);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tag")
											  .contentType(MediaType.APPLICATION_JSON)
											  .content(request))
			   .andExpect(MockMvcResultMatchers.status().isCreated());
		
	}
	
	@Test
	@WithMockUser(username = "adminUser", authorities = {"ROLE_USER"})
	public void testThatCreateTagReturnsCreatedTags() throws Exception
	{
		TagRequest tag = TestDataUtil.createTestTagRequestA();
		String request = objectMapper.writeValueAsString(tag);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/tag")
											  .contentType(MediaType.APPLICATION_JSON)
											  .content(request))
			   .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty())
			   .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("TestTagA"));
		
	}
	
	@Test
	@WithMockUser(username = "adminUser", authorities = {"ROLE_USER"})
	public void testThatDeleteTagReturnsHttp204() throws Exception
	{
		Tag tag = TestDataUtil.createTestTagA();
		Set<String> tagNames = new HashSet<>();
		tagNames.add(tag.getName());
		tagService.createTags(tagNames);
		
		List<Tag> tags = tagService.getAllTags();
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/tag/"+tags.get(0).getId()))
			   .andExpect(MockMvcResultMatchers.status().isNoContent());
		
	}
	
	@Test
	@WithMockUser(username = "adminUser", authorities = {"ROLE_USER"})
	public void testThatGetAllPostsReturnsHttp200() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post"))
			   .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@WithMockUser(username = "adminUser", authorities = {"ROLE_USER"})
	public void testThatGetAllPostsReturnsAllPosts() throws Exception
	{
		Tag tag = TestDataUtil.createTestTagA();
		Set<Tag> tags = new HashSet<>();
		tags.add(tag);
		tagService.createTags(tags.stream().map(Tag::getName).collect(Collectors.toSet()));
		tags = tagService.getAllTags().stream().collect(Collectors.toSet());
		
		Category category = TestDataUtil.createTestCategoryA();
		categoryService.addCategory(category);
		
		User user = TestDataUtil.createTestUserA();
		userService.createUser(user);
		
		Post post = TestDataUtil.createTestPostA(tags, category, user);
		CreatePostRequest createPostRequest = CreatePostRequest.builder()
															   .title(post.getTitle())
															   .content(post.getContent())
															   .categoryId(post.getCategory().getId())
															   .tagIds(post.getTags().stream().map(Tag::getId).collect(Collectors.toSet()))
															   .postStatus(post.getPostStatus())
															   .build();
		postService.createPost(post.getAuthor().getId(), createPostRequest);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty())
			   .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("TestPostA"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("Contents of test post A..."))
			   .andExpect(MockMvcResultMatchers.jsonPath("$[0].category.name").value("TestCategoryA"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$[0].tags[0].name").value("TestTagA"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$[0].author.name").value("TestUserA"));
	}
	
	@Test
	@WithMockUser(username = "adminUser", authorities = {"ROLE_USER"})
	public void testThatGetAllDraftPostsReturnsHttp200() throws Exception
	{
		User user = TestDataUtil.createTestUserA();
		userService.createUser(user);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/draft")
											  .requestAttr("username", user.getId()))
			   .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@WithMockUser(username = "adminUser", authorities = {"ROLE_USER"})
	public void testThatGetAllDraftPostsReturnsAllDraftPosts() throws Exception
	{
		Tag tag = TestDataUtil.createTestTagA();
		Set<Tag> tags = new HashSet<>();
		tags.add(tag);
		tagService.createTags(tags.stream().map(Tag::getName).collect(Collectors.toSet()));
		tags = tagService.getAllTags().stream().collect(Collectors.toSet());
		
		Category category = TestDataUtil.createTestCategoryA();
		categoryService.addCategory(category);
		
		User user = TestDataUtil.createTestUserA();
		userService.createUser(user);
		
		Post post = TestDataUtil.createTestPostB(tags, category, user);
		CreatePostRequest createPostRequest = CreatePostRequest.builder()
															   .title(post.getTitle())
															   .content(post.getContent())
															   .categoryId(post.getCategory().getId())
															   .tagIds(post.getTags().stream().map(Tag::getId).collect(Collectors.toSet()))
															   .postStatus(post.getPostStatus())
															   .build();
		postService.createPost(post.getAuthor().getId(), createPostRequest);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/draft")
											  .requestAttr("username", user.getId()))
			   .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNotEmpty())
			   .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("TestPostB"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$[0].content").value("Contents of test post B..."))
			   .andExpect(MockMvcResultMatchers.jsonPath("$[0].category.name").value("TestCategoryA"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$[0].tags[0].name").value("TestTagA"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$[0].author.name").value("TestUserA"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$[0].postStatus").value("DRAFT"));
	}
	
	@Test
	@WithMockUser(username = "adminUser", authorities = {"ROLE_USER"})
	public void testThatGetOnePostReturnsHttp200() throws Exception
	{
		Tag tag = TestDataUtil.createTestTagA();
		Set<Tag> tags = new HashSet<>();
		tags.add(tag);
		tagService.createTags(tags.stream().map(Tag::getName).collect(Collectors.toSet()));
		tags = tagService.getAllTags().stream().collect(Collectors.toSet());
		
		Category category = TestDataUtil.createTestCategoryA();
		categoryService.addCategory(category);
		
		User user = TestDataUtil.createTestUserA();
		userService.createUser(user);
		
		Post post = TestDataUtil.createTestPostA(tags, category, user);
		CreatePostRequest createPostRequest = CreatePostRequest.builder()
															   .title(post.getTitle())
															   .content(post.getContent())
															   .categoryId(post.getCategory().getId())
															   .tagIds(post.getTags().stream().map(Tag::getId).collect(Collectors.toSet()))
															   .postStatus(post.getPostStatus())
															   .build();
		postService.createPost(post.getAuthor().getId(), createPostRequest);
		
		List<Post> posts = postService.getAllPosts(null, null);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/"+posts.get(0).getId()))
			   .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@WithMockUser(username = "adminUser", authorities = {"ROLE_USER"})
	public void testThatGetOnePostReturnsOnePost() throws Exception
	{
		Tag tag = TestDataUtil.createTestTagA();
		Set<Tag> tags = new HashSet<>();
		tags.add(tag);
		tagService.createTags(tags.stream().map(Tag::getName).collect(Collectors.toSet()));
		tags = tagService.getAllTags().stream().collect(Collectors.toSet());
		
		Category category = TestDataUtil.createTestCategoryA();
		categoryService.addCategory(category);
		
		User user = TestDataUtil.createTestUserA();
		userService.createUser(user);
		
		Post post = TestDataUtil.createTestPostA(tags, category, user);
		CreatePostRequest createPostRequest = CreatePostRequest.builder()
															   .title(post.getTitle())
															   .content(post.getContent())
															   .categoryId(post.getCategory().getId())
															   .tagIds(post.getTags().stream().map(Tag::getId).collect(Collectors.toSet()))
															   .postStatus(post.getPostStatus())
															   .build();
		postService.createPost(post.getAuthor().getId(), createPostRequest);
		
		List<Post> posts = postService.getAllPosts(null, null);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/"+posts.get(0).getId()))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("TestPostA"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Contents of test post A..."))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.category.name").value("TestCategoryA"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.tags[0].name").value("TestTagA"))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.author.name").value("TestUserA"));
	}
}
