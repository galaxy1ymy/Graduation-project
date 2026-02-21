package com.example.employee.manager.service;

import com.example.employee.manager.domain.Notification;
import com.example.employee.manager.domain.NotificationExample;
import com.example.employee.manager.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;



    public NotificationService(NotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
    }

    // 查询所有可用通知
    public List<Notification> getActiveNotifications() {
        NotificationExample example = new NotificationExample();
        example.createCriteria().andIsActiveEqualTo(true); // 只查激活状态
        return notificationMapper.selectByExampleWithBLOBs(example);
    }

    // 根据ID查询
    public Notification getById(Integer id) {
        return notificationMapper.selectByPrimaryKey(id);
    }

    // 创建通知
    public void create(Notification notification) {
        // 设置默认激活状态
        if (notification.getIsActive() == null) {
            notification.setIsActive(true);
        }
        notificationMapper.insertSelective(notification);
    }

    // 更新通知
    public void update(Notification notification) {
        // 更新时也确保是激活状态
        if (notification.getIsActive() == null) {
            notification.setIsActive(true);
        }
        notificationMapper.updateByPrimaryKeySelective(notification);
    }

    // 删除通知
    public void delete(Integer id) {
        notificationMapper.deleteByPrimaryKey(id);
    }


}