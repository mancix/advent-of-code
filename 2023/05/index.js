// Description: Advent of code day 5 solutions

/**
 *
 * @param {String} text
 */
function parser(text) {
  let seeds;
  let mapper = {};
  let map;
  text.split('\n').forEach((line, index) => {
    if (index === 0) {
      seeds = line
        .split('seeds: ')[1]
        .split(' ')
        .map((x) => parseInt(x));
    } else if (line.search('map') !== -1) {
      map = line.split(' map:')[0];
      mapper[map] = [];
    } else if (line !== '') {
      mapper[map].push(line.split(' ').map((x) => parseInt(x)));
    }
  });
  return [seeds, mapper];
}

/**
 *
 * @param {Array<Number>} seeds
 * @param {Array<Array<Number>>} maps
 * @return Array<Number>
 */
function map(seeds, maps) {
  let result = [];
  for (let i = 0; i < seeds.length; i++) {
    let seed = seeds[i];
    for (let j = 0; j < maps.length; j++) {
      if (result[i]) {
        continue;
      }
      let soil = maps[j];
      if (seed >= soil[1] && seed <= soil[1] + soil[2]) {
        result[i] = soil[0] + seed - soil[1];
        continue;
      }
    }
    if (!result[i]) {
      result[i] = seed;
    }
  }
  return result;
}

function solvePartOne(text) {
  let result;
  let [seeds, mapper] = parser(text);
  seedsTemp = seeds;
  Object.values(mapper).forEach((value) => {
    result = map(seedsTemp, value);
    seedsTemp = result;
  });
  let min;
  for (let i = 0; i < result.length; i++) {
    if (min && result[i] < min) {
      min = result[i];
    } else if (!min) {
      min = result[i];
    }
  }
  return min;
}

function solvePartTwo(text) {
  let result;
  let [seedsRaw, mapper] = parser(text);
  let values = Object.values(mapper);
  let chunksResult = [];
  let ranges = [];
  while (seedsRaw.length) {
    let seeds = seedsRaw.splice(0, 2);
    ranges.push({ start: seeds[0], end: seeds[0] + seeds[1] });
    let seedsTemp = [seeds[0], seeds[0] + seeds[1]];
    for (let i = 0; i < values.length; i++) {
      result = map(seedsTemp, values[i]);
      seedsTemp = result;
    }
    chunksResult.push(result[findMinIndex(result)]);
  }
  let index = findMinIndex(chunksResult);
  let range = ranges[index];
  let seedsRange = createSeedsRange(range.start, range.end);
  let middleResult;
  let middleChuncksResult = [];
  let middleRanges = [];
  while (seedsRange.length) {
    let seedMiddleRange = seedsRange.splice(0, 30000);
    middleRanges.push({
      start: seedMiddleRange[0],
      end: seedMiddleRange[seedMiddleRange.length - 1],
    });
    let seedsTemp = [
      seedMiddleRange[0],
      seedMiddleRange[seedMiddleRange.length - 1],
    ];
    for (let i = 0; i < values.length; i++) {
      middleResult = map(seedsTemp, values[i]);
      seedsTemp = middleResult;
    }
    middleChuncksResult.push(middleResult[findMinIndex(middleResult)]);
  }
  let j = findMinIndex(middleChuncksResult);
  range = ranges[index];
  seedsRange = createSeedsRange(range.start, range.end);
  let finalResult;
  for (let i = 0; i < values.length; i++) {
    finalResult = map(seedsRange, values[i]);
    seedsRange = finalResult;
  }
  let k = findMinIndex(finalResult);
  return finalResult[k];
}

function readInput() {
  const fs = require('fs');
  return fs.readFileSync('input.txt').toString();
}

function findMinIndex(arr) {
  let minIndex = 0;
  for (let i = 1; i < arr.length; i++) {
    if (arr[i] < arr[minIndex]) {
      minIndex = i;
    }
  }
  return minIndex;
}

function createSeedsRange(start, end) {
  let result = [];
  for (let i = start; i <= end; i++) {
    result.push(i);
  }
  return result;
}

console.log('part one: ' + solvePartOne(readInput()));
console.log('part two: ' + solvePartTwo(readInput()));

module.exports = { solvePartOne, solvePartTwo };
