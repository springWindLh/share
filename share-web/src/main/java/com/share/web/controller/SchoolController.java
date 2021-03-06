package com.share.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.share.web.entity.School;
import com.share.web.service.ISchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
@RequestMapping("/school")
public class SchoolController extends BaseController {

    private static final String SCHOOLS_URL = "http://data.api.gkcx.eol.cn/soudaxue/queryschool.html?messtype=jsonp";

    @Autowired
    private ISchoolService schoolService;

    @RequestMapping(value = "import_json", method = RequestMethod.POST)
    public ModelAndView importSchools(MultipartFile file) {
        try {
            List<School> schools = JSONArray.parseArray(new String(file.getBytes(), "UTF-8"), School.class);
            if (schools.size() > 0) {
                for (School school : schools) {
                    schoolService.save(school);
                }
                return modelAndView(schools.size());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return modelAndView(false);
    }

    @RequestMapping(value = "init/schools", method = RequestMethod.GET)
    @ResponseBody
    public String initSchools() {
        int page = 0, size = 50;
        List<School> schools;
        do {
            page++;
            schools = this.getRemoteSchools("普通本科", page, size);
            if (schools != null) {
                for (School school : schools) {
                    schoolService.save(school);
                }
            }
        } while (schools != null);
        page = 0;
        do {
            page++;
            schools = this.getRemoteSchools("独立学院", page, size);
            if (schools != null) {
                for (School school : schools) {
                    if (school != null){
                        schoolService.save(school);
                    }
                }
            }
        } while (schools != null);
        return "学校数据初始化完成";
    }

    private List<School> getRemoteSchools(String schooltype, int page, int size) {
        String url = SCHOOLS_URL + "&schooltype=" + schooltype + "&page=" + page + "&size=" + size;
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        response = response.replaceFirst("null\\(", "");
        response = response.substring(0, response.lastIndexOf(");"));
        List<School> schools = JSON.parseArray(JSON.parseObject(response).getString("school"), School.class);
        return schools;
    }

    @RequestMapping(value = "list/provinces", method = RequestMethod.GET)
    public ModelAndView listProvinces() {
        List<String> provinces = schoolService.listProvinces();
        Map<String, Object> object = new HashMap<>();
        object.put("provinces", provinces);
        return modelAndView(object);
    }

    @RequestMapping(value = "list/schools", method = RequestMethod.GET)
    public ModelAndView listSchools(@RequestParam String province, @RequestParam String schooltype) {
        List<School> schools = schoolService.listSchools(province, schooltype);
        Map<String, Object> object = new HashMap<>();
        object.put("schools", schools);
        return modelAndView(object);
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable Integer id) {
        School school = schoolService.findByID(id);
        getSession().setAttribute("school", school);
        return modelAndView(true);
    }

    @RequestMapping(value = "seeAll", method = RequestMethod.GET)
    public ModelAndView seeAll() {
        if (getSession().getAttribute("school") != null) {
            getSession().removeAttribute("school");
        }
        return modelAndView(true);
    }
}
