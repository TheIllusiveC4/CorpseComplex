package top.theillusivec4.corpsecomplex.common;

public interface Setting<T> {

  void importConfig();

  void applyOverride(T override);

}
