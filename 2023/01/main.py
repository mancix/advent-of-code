
def main():
  payload = retrieve_file_payload('input.txt')
  print(sum_numbers_found(payload))

def number_finder(token):
  numbers = {
  'one': 1, 'two': 2, 'three': 3, 'four': 4, 'five': 5, 'six': 6, 'seven': 7, 'eight': 8, 'nine': 9
  }
  first = None
  last = None
  found = None
  for index, letter in enumerate(token):
    if letter.isnumeric():
      found = letter
    else:
      found_index = 0
      for number in numbers:
        litteral_index = token[index:].find(number)
        if litteral_index >= 0 and litteral_index <= found_index:
          found = numbers[number]
          found_index = litteral_index

    if first is None:
      first = found
    last = found

  if first is None and last is None:
    return 0
  return int(str(first) + str(last))

  
def sum_numbers_found(payload):
  total = 0
  for token in payload:
    total += number_finder(token)
  return total

  
def retrieve_file_payload(filename):
  with open(filename, 'r') as file:
    return file.readlines()
  
main()