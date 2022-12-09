package com.wiley.BlogManagementSystem.controller;

import com.wiley.BlogManagementSystem.dao.BlogDao;
import com.wiley.BlogManagementSystem.dao.TagDao;
import com.wiley.BlogManagementSystem.dao.UserDao;
import com.wiley.BlogManagementSystem.model.Blog;
import com.wiley.BlogManagementSystem.model.BlogTagDetail;
import com.wiley.BlogManagementSystem.model.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminController {
    @Autowired
    BlogDao blogDao;
    @Autowired
    TagDao tagDao;
    @Autowired
    UserDao userDao;

    //this is for security
//    @RequestMapping(value="/admin", method=RequestMethod.GET)
//    public String displayAdminPage() {
//        return "admin";
//    }


    @GetMapping("adminDash")
    public String displayAdminDash(Model model){
        List<Blog> blogs = blogDao.getAllBlog();
        model.addAttribute("blogs",blogs);
        List<Tag> tags = tagDao.getAllTags();
        model.addAttribute("tags",tags);
        return "adminDash";
    }
    //    @RequestMapping(value="/adminDash", method=RequestMethod.GET)
//    public String displayAdminPage() {
//
//        return "adminDash";
//    }
    //this loads the page
    @GetMapping("adminCreateBlog")
    public String displayAdminCreateBlog(Model model){
//        List<Tag> tags = tagDao.getAllTags();
//        model.addAttribute("tags", tags);
//        BlogManyTags tagsForm = new BlogManyTags();
//        for (int i = 1; i <= 3; i++) {
//            tagsForm.addTag(new Tag());
//        }
//        model.addAttribute("tagsForm", tagsForm);
        List<Tag> tags = tagDao.getAllTags();
        model.addAttribute("tags", tags);
        List<BlogTagDetail> btd = blogDao.getAllBlogTags();
        model.addAttribute("btd",btd);

        return "adminCreateBlog";
    }

    //this creates the add
    @PostMapping("adminCreateBlog")
    public String addBlog(HttpServletRequest request) throws ParseException {

        //get all the parameters for the fields we want to add
        String title = request.getParameter("title");
        String date = request.getParameter("date");
        String body = request.getParameter("body");
        String isApproved = request.getParameter("isApproved");
        String tag_name = request.getParameter("tag_name");
        int tag_id = Integer.parseInt(request.getParameter("tag_id"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        //Add to Blog Table
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setBody(body);
        blog.setDate(formatter.parse(date));
        blog.setIsApproved(Boolean.parseBoolean(isApproved));
        blogDao.add(blog);


//        Add to Tag Table
        Tag tag = new Tag();
        tag.setTag_id(tag_id);
        tag.setTag_name(tag_name);
//        Add to bridge table
        blogDao.addBlogTag(blog,tag);

        return "redirect:/adminDash";
    }

//    @GetMapping("addTag")
//    public String displayAddTag(){
//        return "addTag";
//    }
//
//    @PostMapping("addTag")
//    public String addTag(HttpServletRequest request){
//        String tag_name = request.getParameter("tag_name");
//        Tag tag = new Tag();
//        tag.setTag_name(tag_name);
//        tagDao.add(tag);
//        return "redirect:/adminDash";
//    }

    //only available to admin
    @GetMapping("deleteBlog")
    public String deleteBlog(Integer blog_id) {
        blogDao.deleteBlogById(blog_id);
        return "redirect:/adminDash";
    }

    @GetMapping("editBlog")
    public String editBlog(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("blog_id"));
        Blog blog = blogDao.getBlogById(id);
        List<Tag> tags = tagDao.getAllTags();
        model.addAttribute("blog", blog);
        model.addAttribute("tags", tags);
        return "editBlog";
    }

    @PostMapping("editBlog")
    public String performEditBlog(HttpServletRequest request) throws ParseException {
        //get all the parameters we want to update, set them, and then update the Blog and bridge table
        int id = Integer.parseInt(request.getParameter("blog_id"));
        int tag_id = Integer.parseInt(request.getParameter("tag_id"));
        Blog blog = blogDao.getBlogById(id);
        Tag tag = tagDao.getTagById(tag_id);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        blog.setTitle(request.getParameter("title"));
        blog.setDate(formatter.parse(request.getParameter("date")));
        blog.setBody(request.getParameter("body"));
        blog.setIsApproved(Boolean.parseBoolean(request.getParameter("isApproved")));
        tag.setTag_id(Integer.parseInt(request.getParameter("tag_id")));
        blogDao.updateBlog(blog);
        blogDao.updateBlogTag(blog,tag);

        return "redirect:/adminDash";
    }

}