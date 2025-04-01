#!/bin/bash

# Arguments: username, password
USERNAME=$1
PASSWORD=$2

# Dump the UI hierarchy
UI_DUMP=$(adb exec-out uiautomator dump /dev/tty | sed 's/UI hierchary dumped to: \/dev\/tty//' | xmllint --format -)

# Function to extract coordinates from bounds
extract_coordinates() {
  local bounds=$1
  local x1=$(echo $bounds | sed -n 's/.*\[\([0-9]*\),\([0-9]*\)\]\[\([0-9]*\),\([0-9]*\)\].*/\1/p')
  local y1=$(echo $bounds | sed -n 's/.*\[\([0-9]*\),\([0-9]*\)\]\[\([0-9]*\),\([0-9]*\)\].*/\2/p')
  local x2=$(echo $bounds | sed -n 's/.*\[\([0-9]*\),\([0-9]*\)\]\[\([0-9]*\),\([0-9]*\)\].*/\3/p')
  local y2=$(echo $bounds | sed -n 's/.*\[\([0-9]*\),\([0-9]*\)\]\[\([0-9]*\),\([0-9]*\)\].*/\4/p')
  local x=$(( (x1 + x2) / 2 ))
  local y=$(( (y1 + y2) / 2 ))
  echo "$x $y"
}

# Function to clear text
clear_text() {
  adb shell input keyevent KEYCODE_MOVE_END
  for i in {1..20}; do
    adb shell input keyevent KEYCODE_DEL
  done
}

# Extract bounds for each element
USERNAME_BOUNDS=$(echo "$UI_DUMP" | xmllint --xpath "string(//node[@resource-id='LoginScreenUsernameField']/@bounds)" -)
PASSWORD_BOUNDS=$(echo "$UI_DUMP" | xmllint --xpath "string(//node[@resource-id='LoginScreenPasswordField']/@bounds)" -)
LOGIN_BUTTON_BOUNDS=$(echo "$UI_DUMP" | xmllint --xpath "string(//node[@resource-id='LoginScreenLoginButton']/@bounds)" -)

# Get coordinates
USERNAME_COORDS=$(extract_coordinates "$USERNAME_BOUNDS")
PASSWORD_COORDS=$(extract_coordinates "$PASSWORD_BOUNDS")
LOGIN_BUTTON_COORDS=$(extract_coordinates "$LOGIN_BUTTON_BOUNDS")

# Input username
adb shell input tap $USERNAME_COORDS
clear_text
adb shell input text "$USERNAME"

# Input password
adb shell input tap $PASSWORD_COORDS
clear_text
adb shell input text "$PASSWORD"

# Click login button
adb shell input tap $LOGIN_BUTTON_COORDS