package com.example.employee.manager.controller;

import com.example.employee.manager.domain.Notification;
import com.example.employee.manager.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
/*@CrossOrigin(origins = "http://localhost:8080") // 允许前端访问*/
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // 获取所有可用通知（员工界面）
    @GetMapping
    public List<Notification> listActive() {
        return notificationService.getActiveNotifications();
    }

    // 根据ID查询（管理员编辑用）
    @GetMapping("/{id}")
    public Notification getById(@PathVariable Integer id) {
        return notificationService.getById(id);
    }

    // 创建通知（管理员）
    @PostMapping
    public void create(@RequestBody Notification notification) {
        notificationService.create(notification);
    }

    // 更新通知（管理员）
    @PutMapping("/{id}")
    public void update(@PathVariable Integer id,
                       @RequestBody Notification notification) {
        notification.setId(id);
        notificationService.update(notification);
    }

    // 删除通知（管理员）
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        notificationService.delete(id);
    }
}
