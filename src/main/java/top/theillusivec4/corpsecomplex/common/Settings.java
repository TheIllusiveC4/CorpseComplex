package top.theillusivec4.corpsecomplex.common;

public interface Settings {

  void importConfig();

  void applyOverride(DeathSettings overrideSettings);
}
