
package net.librec.annotation;


import java.lang.annotation.*;


@Inherited
@Documented
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ModelData {
    String[] value();

//    Class<?> writable() default NullWritable.class;
//
//    Class<?> mapKey() default NullWritable.class;
//
//    Class<?> mapVal() default NullWritable.class;
}
