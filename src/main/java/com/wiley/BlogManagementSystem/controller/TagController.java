package com.wiley.BlogManagementSystem.controller;


import com.wiley.BlogManagementSystem.dao.TagDao;
import com.wiley.BlogManagementSystem.model.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TagController {
    @Autowired
    TagDao tagDao;

    @GetMapping("tags")
    public String displayTags(Model model){
        List<Tag> tags = tagDao.getAllTags();
        model.addAttribute("tags", tags);
        return "tags";
    }

    @GetMapping("addTag")
    public String displayAddTag(){
        return "addTag";
    }

    @PostMapping("addTag")
    public String addTag(HttpServletRequest request){
        String tag_name = request.getParameter("tag_name");
        Tag tag = new Tag();
        tag.setTag_name(tag_name);
        tagDao.add(tag);
        return "redirect:/adminDash";
    }

    @GetMapping("userAddTag")
    public String displayUserAddTag(){
        return "userAddTag";
    }

    @PostMapping("userAddTag")
    public String userAddTag(HttpServletRequest request){
        String tag_name = request.getParameter("tag_name");
        Tag tag = new Tag();
        tag.setTag_name(tag_name);
        tagDao.add(tag);
        return "redirect:/userDash";
    }

    @GetMapping("updateTag")
    public String editTag(Integer tag_id, Model model){
        Tag tag = tagDao.getTagById(tag_id);
        model.addAttribute("tag", tag);
        return "updateTag";
    }

    @GetMapping("deleteTag")
    public String deleteTag(Integer tag_id){
        tagDao.deleteTagById(tag_id);
        return "redirect:/tags";
    }


}