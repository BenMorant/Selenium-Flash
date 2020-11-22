package fr.flash.ecm.donnees;

public enum EnvironnementEnum {
  AVENGERS("avengers"),
  WATCHMEN("watchmen");

  String name;

  EnvironnementEnum(String name) {
    this.name = name;
  }


  public String getName() {
    return name;
  }
}
