antlr4 -no-listener -visitor LabeledExpr.g4 # -visitor is required!!!
javac Calc.java LabeledExpr*.java
java Calc t.expr

#answer
# 193
# 17
# 9