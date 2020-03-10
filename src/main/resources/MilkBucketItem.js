function initializeCoreMod() {
  return {
    'coremodone': {
      'target': {
        'type': 'CLASS',
        'name': 'net.minecraft.item.MilkBucketItem'
      },
      'transformer': function(classNode) {
        print("Initializing transformation ", classNode.toString());
        var Opcodes = Java.type('org.objectweb.asm.Opcodes');
        var JumpInsnNode = Java.type('org.objectweb.asm.tree.JumpInsnNode');
        var LabelNode = Java.type('org.objectweb.asm.tree.LabelNode');
        var methods = classNode.methods;

        for (m in methods) {
          var method = methods[m];

          if (method.name === "onItemUseFinish" || method.name === "func_77654_b") {
            print("Found method onItemUseFinish ", method.toString());
            var code = method.instructions;
            var instr = code.toArray();
            var count = 0;

            for (var i = 0; i < instr.length; i++) {
              var instruction = instr[i];

              if (instruction.getOpcode() === Opcodes.POP) {

                if (count >= 1) {
                  print("Found node ", instr.toString());
                  instruction = instruction.getPrevious();
                  code.remove(instruction.getPrevious());
                  code.remove(instruction.getNext());
                  code.remove(instruction);
                  break;
                }
                count++;
              }
            }
          }
        }
        return classNode;
      }
    }
  }
}
