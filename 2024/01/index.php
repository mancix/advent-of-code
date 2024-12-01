<?php

function parseInputFile($filename) {
    $file = fopen($filename, "r");
    $left = [];
    $right = [];
    while ($line = fgets($file)) {
        $input = explode('   ', trim($line));
        $left[] = (int)$input[0];
        $right[] = (int)$input[1];
    }
    fclose($file);
    return [$left, $right];
}

//part 1

[$left, $right] = parseInputFile("input.txt");

sort($left);
sort($right);


$result = 0;
foreach ($left as $i => $l) {
  $result += abs($l - $right[$i]);
}

var_dump($result);

// part 2

[$left, $right] = parseInputFile("input.txt");
$count = [];

foreach ($right as $r) {
  $count[$r] = isset($count[$r]) ? $count[$r] + 1 : 1;
}

var_dump($count);
$result = 0;
foreach ($left as $l) {
  $result += $l * ($count[$l] ?? 0);
}

var_dump($result);