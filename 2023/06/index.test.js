const { solvePartOne, solvePartTwo } = require('./index.js');

let testText = `Time:      7  15   30
Distance:  9  40  200`;

describe('2023/06', () => {
  test('should return the correct answer for the test case part one', () => {
    expect(solvePartOne(testText)).toBe(288);
  });
  test('should return the correct answer for the test case part two', () => {
    expect(solvePartTwo(testText)).toBe(71503);
  });
});
