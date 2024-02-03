from main import sum_numbers_found

def test_correct_sum_with_numerics():
  tokens = [
    '1abc2',
    'pqr3stu8vwx',
    'a1b2c3d4e5f',
    'treb7uchet',
  ]

  assert sum_numbers_found(tokens) == 142

def test_correct_sum_with_digit_strings_and_numeric():
  tokens = [
    'two1nine',
    'eightwothree',
    'abcone2threexyz',
    'xtwone3four',
    '4nineeightseven2',
    'zoneight234',
    '7pqrstsixteen',
  ]

  assert sum_numbers_found(tokens) == 281