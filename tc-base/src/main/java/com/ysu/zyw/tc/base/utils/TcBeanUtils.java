package com.ysu.zyw.tc.base.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * TcBeanUtils defines bean related operations
 *
 * @author yaowu.zhang
 */
@Slf4j
@UtilityClass
public class TcBeanUtils {

    public static <T> T copyProperties(@Nonnull Object source, @Nonnull T target) {
        checkNotNull(source);
        checkNotNull(target);
        checkArgument(!(source instanceof Map) && !(target instanceof Map));
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public static <T> List<T> copyProperties(@Nonnull Object source, @Nonnull List<T> targets) {
        checkNotNull(source);
        checkNotNull(targets);
        targets.forEach(target -> BeanUtils.copyProperties(source, target));
        return targets;
    }

    public static <T> T deepCopyProperties(@Nonnull Object source, @Nonnull TypeReference<T> typeReference) {
        checkNotNull(source);
        checkNotNull(typeReference);
        return TcSerializationUtils.readJson(TcSerializationUtils.writeJson(source), typeReference);
    }

}
