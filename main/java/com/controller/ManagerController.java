package com.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.entitys.Lable;
import com.entitys.Order;
import com.entitys.User;
import com.services.LableServer;
import com.services.OrderLibraryServer;
import com.services.UserLibraryServer;

@Controller
public class ManagerController {
    @Autowired
    private LableServer lableServer;
    @Autowired
    private OrderLibraryServer orderLibraryServer;
    @Autowired
    private UserLibraryServer userLibraryServer;
    //主页面跳转到管理员页面
    @RequestMapping("/manager/center")
    public String jumpToManagerCenter(HttpServletRequest request)
    {
        List<Lable> lables  = lableServer.getAllLables();
        request.setAttribute("lables", lables);
        return "manager/managerLable";
    }

    //跳转到管理书籍标签页面
    @RequestMapping("/manager/lable")
    public String jumpToManagerLablePage(HttpServletRequest request)
    {
        List<Lable> lables  = lableServer.getAllLables();
        request.setAttribute("lables", lables);
        return "manager/managerLable";
    }

    //跳转到管理用户页面
    @RequestMapping("/manager/manageuser")
    public String jumpToManagerUserPage(HttpServletRequest request)
    {
        List<User> userlist = userLibraryServer.getAllUser();
        request.setAttribute("userlist", userlist);
        return "manager/managerUser";
    }

    //跳转到未处理的申诉订单
    @RequestMapping("/manager/handleappeal")
    public String jumpToManagerHandleAppealPage(HttpServletRequest request)
    {
        List<Order> orderList = orderLibraryServer.getNotHandleAppeal();
        request.setAttribute("orderlist", orderList);
        request.setAttribute("kindoforder", 0);
        return "manager/managerHandleAppeal";
    }


    //跳转到已处理的申诉
    @RequestMapping("/manager/hashandleappeal")
    public String jumpToHasHandleAppealPage(HttpServletRequest request)
    {
        List<Order> orderlist = orderLibraryServer.getHasHandleAppeal();
        request.setAttribute("orderlist", orderlist);
        request.setAttribute("kindoforder", 2);
        return "manager/managerHandleAppeal";
    }


    //添加标签
    @RequestMapping("/manager/addlable")
    @ResponseBody
    public ResponseEntity<?> addLable(@RequestParam("lable") String lable)
    {
        int flag = lableServer.addLable(lable);
        if (flag == -1)
        {  
            return new ResponseEntity<>("标签已存在", HttpStatus.OK);  
        } 
        else if(flag == 0)
        {  
            return new ResponseEntity<>("标签添加失败", HttpStatus.BAD_REQUEST);  
        }
        else
        {
            return new ResponseEntity<>("标签添加成功", HttpStatus.BAD_REQUEST);  
        }
    }

    //修改标签
    @RequestMapping("/manager/editlable/{oldlable}/{newlable}")
    @ResponseBody
    public boolean editLable(@PathVariable("oldlable") Integer oldlableid, @PathVariable("newlable") String newlable)
    {
        Lable lable = lableServer.getLableById(oldlableid);
        if (lable == null)
            return false;
        boolean flag = lableServer.editLable(lable.getLable(), newlable);
        return flag;
    }


    //处理申述
    @RequestMapping("/manager/handleappeal/{orderid}/{outcome}")
    @ResponseBody
    public boolean handleAppeal(@PathVariable("orderid") Integer orderid, @PathVariable("outcome") int outcome)
    {
        Order order = orderLibraryServer.getOrderById(orderid);
        if(order == null)
            return false;
        order.setAppealoutcome(outcome);
        orderLibraryServer.saveOrder(order);
        return true;
    }


    //添加用户
    //需要通过表单
    @RequestMapping("/manager/addUser")
    public ResponseEntity<?> addUser(@RequestParam("username") String username)
    {
        boolean flag = userLibraryServer.checkUserDuplicated(username);
        if (flag)
            return new ResponseEntity<>("该用户名已存在!", HttpStatus.BAD_REQUEST);

        flag = userLibraryServer.addUser(username);
        if (flag)
            return new ResponseEntity<>("用户添加成功!",HttpStatus.OK);

        return new ResponseEntity<>("用户添加失败!", HttpStatus.BAD_REQUEST);
    }


    //修改用户密码
    @RequestMapping("/manager/editPassword/{userid}/{password}")
    @ResponseBody
    public boolean editUserPassword(@PathVariable("userid") Integer userid, @PathVariable("password") String password)
    {
        System.out.println("改密码");
        User user = userLibraryServer.getUserById(userid);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(password));
        userLibraryServer.updateUserPassword(user);
        return true;
    }


    //删除用户
    @RequestMapping("/manager/deleteUser/{userid}")
    public boolean deleteUser(@PathVariable("userid") Integer userid)
    {
        boolean flag = userLibraryServer.deleteUser(userid);
        return flag;
    }


    //批量通过txt文件批量添加用户
    @RequestMapping("/manager/addALotsOfUser")
    public ResponseEntity<?> addALotsOfUser(@RequestParam("file") MultipartFile file)
    {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }

        try {
            //使用ResourceUtils保存位置未知避免找不到路径报错
            //于是将文件保存到外部目录中
            String uploadDir = "D:\\F盘\\Desktop\\软件工程导论\\项目实验\\OuterProjectFileSaving2\\manager";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 保存文件到指定路径
            // 保存文件到外部目录
            Path uploadPath = Paths.get(uploadDir, file.getOriginalFilename());
            file.transferTo(uploadPath.toFile());

            
            List<String> unputList = userLibraryServer.addUsersByFile(uploadPath.toString());

            //期望告知管理员，哪些学生未添加成功
            
            return ResponseEntity.ok("File uploaded and processed successfully: " + uploadPath.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }
}
