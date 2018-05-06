import re
from collections import defaultdict


def find_optiaml_pass(log_file):
    cost_info = defaultdict(list)
    cost_pat = re.compile(r'Cost\s[\d]+.[\d]+')
    pass_pat = re.compile(r'Pass\s[\d]+')
    with open(log_file, 'r') as flog:
        for line in flog:
            if not 'Cost' in line: continue
            pass_id = pass_pat.findall(line.strip())[0]
            cost = float(cost_pat.findall(line.strip())[0].replace('Cost ', ''))
            cost_info[pass_id].append(cost)
    print("optimal pass : %s" % sorted(
        cost_info.iteritems(),
        key=lambda x: sum(x[1]) / (len(x[1])),
        reverse=False)[0][0])


if __name__ == "__main__":
    find_optiaml_pass("train.log")
