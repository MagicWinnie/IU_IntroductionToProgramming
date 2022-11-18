import time
import subprocess

print('Compiling Java...')
subprocess.run(['javac', 'Main.java'])

print('Running program...')
start = time.time()
subprocess.run(['java', 'Main'])
print(time.time() - start)
