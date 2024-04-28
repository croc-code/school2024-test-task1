conan install Conanfile.txt --build=missing
cd build
cmake .. -DCMAKE_TOOLCHAIN_FILE="./build/generators/conan_toolchain.cmake"
cmake --build . --config Release
cmake --install .
cd ..\solution
.\test_app.exe