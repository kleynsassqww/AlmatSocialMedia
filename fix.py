import glob
for f in glob.glob('src/main/resources/templates/*.html'):
    try:
        with open(f, 'r', encoding='utf-8') as file:
            data = file.read()
        data = data.replace('\', '\')
        data = data.replace('\', '\')
        with open(f, 'w', encoding='utf-8') as file:
            file.write(data)
        print('Updated ' + f)
    except Exception as e:
        print('Error parsing ' + f + ': ' + str(e))
