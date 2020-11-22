package fr.flash.util.annotations;


public @interface Feature {

  String[] value() default {"Default Feature"};
}
