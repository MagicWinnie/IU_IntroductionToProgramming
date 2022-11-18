import random
import itertools

f = open('input.txt', 'w')

n = random.randint(1000, 1000)
f.write(f'{n}\n')

coords = list(itertools.product([i for i in range(1, n + 1)], repeat=2))
random.shuffle(coords)

m = random.randint(2, n * n)
f.write(f'{m}\n')

index = 0
king1_coord = coords[index]
index += 1
f.write(f"King Black {king1_coord[0]} {king1_coord[1]}\n")
king2_coord = coords[index]
index += 1
f.write(f"King White {king2_coord[0]} {king2_coord[1]}\n")

for _ in range(m):
    piece_coord = coords[index]
    index += 1
    piece_name = random.choice(('Pawn', 'Knight', 'Rook', 'Queen', 'Bishop'))
    piece_color = random.choice(('White', 'Black'))
    f.write(f"{piece_name} {piece_color} {piece_coord[0]} {piece_coord[1]}\n")

f.close()