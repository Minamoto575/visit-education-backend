package cn.krl.visiteducationbackend.controller;

import cn.krl.visiteducationbackend.common.annotation.PassToken;
import cn.krl.visiteducationbackend.common.response.ResponseWrapper;
import cn.krl.visiteducationbackend.model.vo.Notice;
import cn.krl.visiteducationbackend.service.INoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kuang
 * @description 通知控制器
 * @date 2021/12/15  17:09
 */
@RestController
@Api(tags = "通知的api")
@RequestMapping("/notice")
@Slf4j
public class NoticeController {
    @Autowired
    private INoticeService noticeService;

    /**
     * @param content
     * @description 发布通知
     * @author kuang
     * @date 2021/12/15
     */
    @PostMapping("/post")
    @ApiOperation("发布一条新的通知")
    public ResponseWrapper postNotice(@RequestParam("content") String content, @RequestHeader("token") String token) {
        ResponseWrapper responseWrapper;
        try {
            noticeService.insert(content);
            responseWrapper = ResponseWrapper.markSuccess();
        } catch (Exception e) {
            responseWrapper = ResponseWrapper.markError();
            log.error("通知发布失败");
            e.printStackTrace();
        }
        return responseWrapper;
    }

    /**
     * @description 获取最新的通知
     * @author kuang
     * @date 2021/12/15
     */
    @GetMapping("/getLatest")
    @ApiOperation("获取最新的通知")
    @PassToken
    public ResponseWrapper getLatestNotice() {
        ResponseWrapper responseWrapper;
        Notice notice = noticeService.getLatest();
        if (notice != null) {
            responseWrapper = ResponseWrapper.markSuccess();
            responseWrapper.setExtra("notice", notice);
        } else {
            responseWrapper = ResponseWrapper.markError();
            log.error("没有最新通知");
        }
        return responseWrapper;
    }

}
