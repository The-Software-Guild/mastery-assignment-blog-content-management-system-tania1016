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

@Controller
public class UserController {
    @Autowired
    BlogDao blogDao;
    @Autowired
    TagDao tagDao;
    @Autowired
    UserDao userDao;

    //this is for security
    @RequestMapping(value="/user", method=RequestMethod.GET)
    public String displayUserPage() {
        return "user";
    }

    @GetMapping("userDash")
    public String displayUserDash(Model model){
        List<Blog> blogs = blogDao.getAllApprovedBlog();
        model.addAttribute("blogs",blogs);
        List<Tag> tags = tagDao.getAllTags();
        model.addAttribute("tags",tags);
        return "userDash";
    }
    @GetMapping("createBlog")
    public String displayCreateBlog(Model model){
        List<Tag> tags = tagDao.getAllTags();
        model.addAttribute("tags", tags);
        List<BlogTagDetail> btd = blogDao.getAllBlogTags();
        model.addAttribute("btd",btd);
        return "createBlog";
    }

    //this is only available to manager
    @PostMapping("createBlog")
    public String addBlogByManager(HttpServletRequest request) throws ParseException {
        //get all the parameters for the fields we want to add
        String title = request.getParameter("title");
        String body = request.getParameter("body");
        String date = request.getParameter("date");
//         String isApproved= request.getParameter("isApproved");

        String tag_name = request.getParameter("Tag_name");
        int tag_id = Integer.parseInt(request.getParameter("tag_id"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        //Add to Blog Table
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setBody(body);
        blog.setDate(formatter.parse(date));
        blog.setIsApproved(false);// Set to false because only admin can approve
        blogDao.add(blog);

        //Add to Tag Table
        Tag tag = new Tag();
        tag.setTag_id(tag_id);
        tag.setTag_name(tag_name);
        //Add to bridge table
        blogDao.addBlogTag(blog,tag);

        return "redirect:/userDash";
    }
//    @GetMapping("userAddTag")
//    public String displayUserAddTag(){
//        return "userAddTag";
//    }
//
//    @PostMapping("userAddTag")
//    public String userAddTag(HttpServletRequest request){
//        String tag_name = request.getParameter("tag_name");
//        Tag tag = new Tag();
//        tag.setTag_name(tag_name);
//        tagDao.add(tag);
//        return "redirect:/userDash";
//    }
}