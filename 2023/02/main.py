def parser(text):
  result = {}
  lines = text.split("\n")
  for line in lines:
    start = line.find('Game')
    end = line.find(':')
    id = int(line[start+5:end])
    matches = line[end+1:].split(";")
    result[id] = {}
    for match in matches:
      cubes = match.split(",")
      for cube in cubes:
        numbers_and_colors = cube.strip().split(' ')
        number = int(numbers_and_colors[0])
        color = numbers_and_colors[1]
        if (color in result[id] and result[id][color] < number) or color not in result[id]:
          result[id][color] = number
  return result

# part 1
def check(results_to_check, available_cubes):
  result = 0
  for game_id in results_to_check:
    game_check = True
    for color in results_to_check[game_id]:
      if color in available_cubes and available_cubes[color] < results_to_check[game_id][color]:
        game_check = False
    if game_check:
      result += game_id

  return result

def generate_power(parsed_result):
  result = 0
  for game_id in parsed_result:
    power = 1
    for color in parsed_result[game_id]:
      power *= parsed_result[game_id][color]
    result += power
  return result

def retrieve_file_payload(filename):
  with open(filename, 'r') as file:
    return file.read()
  
games = retrieve_file_payload('input.txt')
parsed_result = parser(games)
print('part one:')
result_part_one = check(parsed_result,{'red': 12, 'green': 13, 'blue': 14})
print(result_part_one)
print('part two:')
result_part_two = generate_power(parsed_result)
print(result_part_two)