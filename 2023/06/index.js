const { count } = require('console');

let testText = `Time:      7  15   30
Distance:  9  40  200`;

function parser(text) {
  let result = [];
  let lines = text.split('\n');
  lines[0]
    .split('Time:')[1]
    .trim()
    .split(' ')
    .forEach((item) => {
      if (item) {
        result.push({
          time: parseInt(item),
        });
      }
    });
  lines[1]
    .split('Distance:')[1]
    .trim()
    .split(' ')
    .filter((item) => {
      if (item) {
        return item;
      }
    })
    .forEach((item, index) => {
      result[index].distance = parseInt(item);
    });
  return result;
}

function countWinningPossibilities(race) {
  let count = 0;
  for (let i = 0; i <= race.time; i++) {
    let result = (race.time - i) * i;
    if (result > race.distance) {
      count++;
    }
  }
  return count;
}

function solvePartOne(text) {
  let parsedData = parser(text);
  let result = 1;
  for (i = 0; i < parsedData.length; i++) {
    let countedVictories = countWinningPossibilities(parsedData[i]);
    if (countedVictories > 0) {
      result *= countedVictories;
    }
  }
  return result;
}

function solvePartTwo(text) {
  let parsedData = parser(text);
  let time = '',
    distance = '';
  parsedData.forEach((item) => {
    time += item.time.toString();
    distance += item.distance.toString();
  });
  time = parseInt(time);
  distance = parseInt(distance);
  return countWinningPossibilities({ time, distance });
}

function readInput() {
  const fs = require('fs');
  return fs.readFileSync('input.txt').toString();
}

console.log('part one: ' + solvePartOne(readInput()));
console.log('part two: ' + solvePartTwo(readInput()));

module.exports = {
  solvePartOne,
  solvePartTwo,
};
