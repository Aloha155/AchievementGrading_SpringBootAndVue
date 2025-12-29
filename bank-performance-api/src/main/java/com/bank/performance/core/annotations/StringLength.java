package com.bank.performance.core.annotations;

import com.bank.performance.core.util.RegexUtil;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 验证字符的长度，中文2个长度，英文一个长度，不验证为空的情况
 *
 * @author X-xg
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {StringLength.PriceChecker.class})
public @interface StringLength {

    String message() default "内容不正确";

    //1000个汉字，2000个英文字符
    int min() default 2;
    int max() default 2000;

    //不能省
    Class<?>[] groups() default {};

    //不能省
    Class<? extends Payload>[] payload() default {};

    class PriceChecker implements ConstraintValidator<StringLength, String> {
        int min = 2;
        int max = 2000;
        @Override
        public void initialize(StringLength constraintAnnotation) {
            this.min = constraintAnnotation.min();
            this.max = constraintAnnotation.max();
        }
        @Override
        public boolean isValid(String text, ConstraintValidatorContext ctx) {
            if (RegexUtil.checkStringIsNull(text)) {
                return true;
            }
            int length = 0;
            String chinese = "[\u4e00-\u9fa5]";
            for (int i = 0; i < text.length(); i++) {
                String temp = text.substring(i, i + 1);
                if (temp.matches(chinese)) {
                    length += 2;
                } else {
                    length += 1;
                }
            }
            return this.min <= length && max >= length;
        }
    }
}
