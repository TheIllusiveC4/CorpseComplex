package top.theillusivec4.corpsecomplex.common.modules;

public interface Setting<T> {

  void importConfig();

  void applyOverride(T override);

}
