conan install conanfile.txt --build=missing
cmake ./build -DCMAKE_TOOLCHAIN_FILE="./build/generators/conan_toolchain.cmake"
cmake --build build --config Release
cmake --install ./build
.\solution\school2024_test_task1.exe