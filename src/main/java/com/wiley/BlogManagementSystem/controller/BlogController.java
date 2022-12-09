package com.wiley.BlogManagementSystem.controller;

import com.wiley.BlogManagementSystem.dao.BlogDao;
import com.wiley.BlogManagementSystem.dao.TagDao;
import com.wiley.BlogManagementSystem.model.Blog;
import com.wiley.BlogManagementSystem.model.BlogTagDetail;
import com.wiley.BlogManagementSystem.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class BlogController {

    @Autowired
    BlogDao blogDao;

    @Autowired
    TagDao tagDao;

    @GetMapping("index")
    public String getIndex(Model model){
        List<Blog> blogs = blogDao.getLastThreeBlogs();
        model.addAttribute("blogs",blogs);
        return "index";
    }

    @GetMapping("userIndex")
    public String getUserIndex(Model model){
        List<Blog> blogs = blogDao.getLastThreeBlogs();
        model.addAttribute("blogs",blogs);
        return "userIndex";
    }

    @GetMapping("readSingleBlog")
    public String getBlogDetails(Model model, Integer blog_id){
        Blog blog = blogDao.getBlogById(blog_id);
        BlogTagDetail btd = blogDao.getSingleBlogTags(blog_id);
        model.addAttribute("blog", blog);
        model.addAttribute("btd",btd);
        return "readSingleBlog";
    }

    @GetMapping("userReadSingleBlog")
    public String getUserBlogDetails(Model model, Integer blog_id){
        Blog blog = blogDao.getBlogById(blog_id);
        BlogTagDetail btd = blogDao.getSingleBlogTags(blog_id);
        model.addAttribute("blog", blog);
        model.addAttribute("btd",btd);
        return "userReadSingleBlog";
    }

    @GetMapping("checkboxes")
    public String getCheckboxes(Model model){
        model.addAttribute("checkboxes");
        return "checkboxes";
    }
    @PostMapping("checkboxes")
    public String postCheckboxes(String[] interest) {
        System.out.println(Arrays.toString(interest));
        return "checkboxes";
    }

    @GetMapping("allBlogs")
    public String displayBlogs(Model model){
//        List<BlogTagDetail> blogs = blogDao.getAllBlogTags();
//        model.addAttribute("blogs", blogs);
        //I think this is where we need to only list blogs that are approved
        List<Blog> blogs = blogDao.getAllApprovedBlog();
        model.addAttribute("blogs",blogs);

        List<Tag> tags= tagDao.getAllTags();
        model.addAttribute("tags", tags);
        return "allBlogs";
    }

    @GetMapping("userAllBlogs")
    public String displayUserAllBlogs(Model model){
//        List<BlogTagDetail> blogs = blogDao.getAllBlogTags();
//        model.addAttribute("blogs", blogs);
        //I think this is where we need to only list blogs that are approved
        List<Blog> blogs = blogDao.getAllBlog();
        model.addAttribute("blogs",blogs);

        List<Tag> tags= tagDao.getAllTags();
        model.addAttribute("tags", tags);
        return "userAllBlogs";
    }

    @GetMapping("activeBlogs")
    public String displayActiveBlogs(Model model){
        List<Blog> activeBlogs = blogDao.getAllActiveBlogs();
        model.addAttribute("activeBlogs", activeBlogs);
        return "activeBlogs";
    }

    @GetMapping("recentBlogs")
    public String displayRecentBlogs(Model model){
        List<Blog> recentBlogs = blogDao.getRecentBlogs();
        model.addAttribute("recentBlogs", recentBlogs);
        return "recentBlogs";
    }

    @GetMapping("privacy")
    public String privacy() {
        return "privacy";
    }

    @GetMapping("userPrivacy")
    public String userPrivacy() {
        return "userPrivacy";
    }

}