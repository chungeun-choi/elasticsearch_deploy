import subprocess
import os
import sys


def run_docker_compose(file_path):
    try:
        result = subprocess.run(["docker","compose", "-f", file_path, "up", "d"], check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        print(result.stdout.decode())
    except subprocess.CalledProcessError as e:
        print(f"Error occurred: {e}")
        print(e.stderr.decode())

def run_all_yml_in_directory(directory_path):
    for root, dirs, files in os.walk(directory_path):
        for file in files:
            if file.endswith(".yml"):
                yml_path = os.path.join(root, file)
                print(f"Running docker-compose for: {yml_path}")
                run_docker_compose(yml_path)

# 실행
directory_path = sys.argv[1]
run_all_yml_in_directory(directory_path)
