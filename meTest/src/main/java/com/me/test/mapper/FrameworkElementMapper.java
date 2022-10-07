package com.me.test.mapper;

import com.me.test.pojo.FrameworkElement;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 人一能之,己百之；人十能之,己千之。果能此道矣,虽愚必明,虽柔必强。
 *
 * @author wolify
 * @version 1.0
 * @date 2022/10/4 14:00:18
 */
@Repository
@Mapper
public interface FrameworkElementMapper {
    List<FrameworkElement> queryAll();
}
