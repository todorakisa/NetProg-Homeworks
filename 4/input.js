regExps = {
"exercise_1": /[A-Z][a-z]+/,
"exercise_2": /088[1-9]\d{6}/,
"exercise_3": /[^\d]+|2/,
"exercise_4": /^[^._0-9n].*$/,
"exercise_5": /1[0-5]00|999|1[0-4][0-9][0-9]/,
"exercise_6": /class=['"].*['"]/
};
cssSelectors = {
"exercise_1": "css > item > java",
"exercise_2": "css > different .diffClass",
"exercise_3": "css > item java.class1 tag.class1.class2",
"exercise_4": "css > item + item + item ",
"exercise_5": "css > item > tag > java.class1 + java.class1",
"exercise_6": "css > item > item > item > item > item",
"exercise_7": "css > different #diffId2 > java + java",
"exercise_8": "css > #someId"
};
